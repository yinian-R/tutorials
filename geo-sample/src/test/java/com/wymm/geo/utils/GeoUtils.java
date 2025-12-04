package com.wymm.geo.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CoordinateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.wymm.geo.utils.transform.CoordinateTransform;
import com.wymm.geo.utils.transform.CoordinateTransformer;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.GeometryTransformer;
import org.locationtech.jts.io.WKTReader;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class GeoUtils {
    
    /**
     * WKT格式的几何数据 转换成 geojson
     *
     * @param wkt
     * @return geojson，最外层无 FeatureCollection，json仅包含当前图形
     */
    @SneakyThrows
    public static String convertWktToGeometryJson(String wkt) {
        if (ObjectUtils.isEmpty(wkt)) {
            return null;
        }
        Geometry geometry = convertWktToGeometry(wkt);
        
        // 将要素集合转换为 GeoJSON
        return convertGeojsonString(geometry);
    }
    
    /**
     * WKT格式的几何数据 转换成 geojson
     *
     * @param wkt
     * @return geojson。格式：{"type":"Feature" ...}
     */
    @SneakyThrows
    public static String convertWktToFeatureJson(String wkt) {
        if (ObjectUtils.isEmpty(wkt)) {
            return null;
        }
        SimpleFeature feature = convertWktToFeature(wkt);
        
        // 将要素集合转换为 GeoJSON
        return convertGeojsonString(feature);
    }
    
    /**
     * WKT格式的几何数据 转换成 geojson
     *
     * @param wkt
     * @return geojson。格式：{"type":"FeatureCollection" ...}
     */
    @SneakyThrows
    public static String convertWktToFeatureCollectionJson(String wkt) {
        if (ObjectUtils.isEmpty(wkt)) {
            return null;
        }
        SimpleFeatureCollection featureCollection = convertWktToFeatureCollection(wkt);
        
        // 将要素集合转换为 GeoJSON
        return convertGeojsonString(featureCollection);
    }
    
    /**
     * WKT格式的几何数据 转换成 geojson
     *
     * @param geojsonText，最外层无 FeatureCollection，json仅包含当前图形
     * @return wkt
     */
    @SneakyThrows
    public static String convertGeometryJsonToWkt(String geojsonText) {
        if (ObjectUtils.isEmpty(geojsonText)) {
            return null;
        }
        Geometry geometry = convertGeometry(geojsonText);
        
        // 将要素转换为 WKT
        return geometry.toText();
    }
    
    /**
     * WKT格式的几何数据 转换成 geojson
     *
     * @param geojsonText。格式：{"type":"Feature" ...}
     * @return
     */
    @SneakyThrows
    public static String convertFeatureJsonToWkt(String geojsonText) {
        if (ObjectUtils.isEmpty(geojsonText)) {
            return null;
        }
        SimpleFeature feature = convertFeature(geojsonText);
        
        // 将要素集合转换为 WKT
        Geometry geometry = (Geometry)feature.getDefaultGeometry();
        return geometry.toText();
    }
    
    /**
     * WKT格式的几何数据 转换成 geojson
     *
     * @param wkt
     * @return
     */
    @SneakyThrows
    public static Geometry convertWktToGeometry(String wkt) {
        WKTReader reader = new WKTReader();
        return reader.read(wkt);
    }
    
    /**
     * WKT格式的几何数据 转换成 geojson
     *
     * @param wkt
     * @return
     */
    @SneakyThrows
    public static SimpleFeature convertWktToFeature(String wkt) {
        if (ObjectUtils.isEmpty(wkt)) {
            return null;
        }
        Geometry geometry = convertWktToGeometry(wkt);
        
        // 创建一个简单的几何要素
        // 定义属性，这里仅包含一个几何属性
        SimpleFeatureType featureType = DataUtilities.createType("FeatureCollection", "the_geom:Geometry,name:String");
        
        // 创建一个包含几何要素的 SimpleFeature 对象
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(featureType);
        builder.add(geometry); // 添加几何属性
        return builder.buildFeature(null); // 特征ID
    }
    
    /**
     * WKT格式的几何数据 转换成 geojson
     *
     * @param wkt
     * @return
     */
    @SneakyThrows
    public static SimpleFeatureCollection convertWktToFeatureCollection(String wkt) {
        if (ObjectUtils.isEmpty(wkt)) {
            return null;
        }
        SimpleFeature feature = convertWktToFeature(wkt);
        // 创建要素集合
        return DataUtilities.collection(feature);
    }
    
    
    /**
     * 将GeoJSON转换为SimpleFeatureCollection
     *
     * @param geoJsonText {"type":"FeatureCollection" ...}
     * @return SimpleFeatureCollection
     */
    @SneakyThrows
    public static SimpleFeatureCollection convertSimpleFeatureCollection(String geoJsonText) {
        FeatureJSON fjson = new FeatureJSON();
        return (SimpleFeatureCollection) fjson.readFeatureCollection(geoJsonText);
    }
    
    /**
     * 将GeoJSON转换为Feature
     *
     * @param geoJsonText {"type": "Feature" ...}
     * @return Feature
     */
    @SneakyThrows
    public static SimpleFeature convertFeature(String geoJsonText) {
        FeatureJSON fjson = new FeatureJSON();
        return fjson.readFeature(geoJsonText);
    }
    
    /**
     * 将GeoJSON转换为Geometry
     *
     * @param geoJsonText 最外层无 FeatureCollection，json仅包含当前图形
     * @return Feature
     */
    @SneakyThrows
    public static Geometry convertGeometry(String geoJsonText) {
        GeometryJSON fjson = new GeometryJSON();
        return fjson.read(geoJsonText);
    }
    
    /**
     * 将经纬度转换为点
     *
     * @param lon 经度
     * @param lat 纬度
     * @return Point
     */
    public static Point convertPoint(double lon, double lat) {
        // 创建几何工厂
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        
        // 创建点
        Coordinate coord = new Coordinate(lon, lat);
        return geometryFactory.createPoint(coord);
    }
    
    /**
     * 将要素集合转换为 GeoJSON
     *
     * @param featureCollection
     * @return
     */
    @SneakyThrows
    public static String convertGeojsonString(SimpleFeatureCollection featureCollection) {
        StringWriter writer = null;
        try {
            FeatureJSON fjson = new FeatureJSON(new GeometryJSON(9));
            writer = new StringWriter();
            fjson.writeFeatureCollection(featureCollection, writer);
            return writer.toString();
        } finally {
            IoUtil.close(writer);
        }
    }
    
    /**
     * 将要素转换为 GeoJSON
     *
     * @param feature
     * @return
     */
    @SneakyThrows
    public static String convertGeojsonString(SimpleFeature feature) {
        StringWriter writer = null;
        try {
            FeatureJSON fjson = new FeatureJSON(new GeometryJSON(9));
            writer = new StringWriter();
            fjson.writeFeature(feature, writer);
            return writer.toString();
        } finally {
            IoUtil.close(writer);
        }
    }
    
    /**
     * 将 Geometry 转换为 GeoJSON
     *
     * @param geometry
     * @return
     */
    @SneakyThrows
    public static String convertGeojsonString(Geometry geometry) {
        StringWriter writer = null;
        try {
            GeometryJSON gjson = new GeometryJSON(9);
            writer = new StringWriter();
            gjson.write(geometry, writer);
            return writer.toString();
        } finally {
            IoUtil.close(writer);
        }
    }
    
    /**
     * 将坐标列表转换为线段
     *
     * @param coordinates
     * @return
     */
    public static LineString convertLineString(Coordinate[] coordinates) {
        if (ObjectUtil.isNull(coordinates)) {
            return null;
        }
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        return geometryFactory.createLineString(coordinates);
    }
    
    /**
     * 将坐标列表转换为线段
     *
     * @param list
     * @return
     */
    public static LineString convertLineString(List<CoordinateUtil.Coordinate> list) {
        if (ObjectUtil.isNull(list)) {
            return null;
        }
        Coordinate[] coordinates = list.stream()
                .map(item -> new Coordinate(item.getLng(), item.getLat()))
                .toArray(Coordinate[]::new);
        return convertLineString(coordinates);
    }
    
    /**
     * 获取GeoJSON中的坐标
     *
     * @param geoJson SimpleFeatureCollection json字符串 或者 SimpleFeature json字符串
     * @return 所有坐标
     */
    @SneakyThrows
    public static Set<Coordinate> convertCoordinate(String geoJson) {
        Set<Coordinate> result = new HashSet<>();
        
        SimpleFeatureCollection featureCollection = convertSimpleFeatureCollection(geoJson);
        if (!featureCollection.isEmpty()) {
            // 遍历 FeatureCollection 中的每个 Feature
            try (FeatureIterator features = featureCollection.features()) {
                while (features.hasNext()) {
                    Feature feature = features.next();
                    
                    // 获取 Feature 的属性
                    Geometry geometry = (Geometry) feature.getDefaultGeometryProperty().getValue();
                    
                    Coordinate[] coordinates = geometry.getCoordinates();
                    result.addAll(Arrays.asList(coordinates));
                }
                return result;
            }
        }
        
        SimpleFeature simpleFeature = convertFeature(geoJson);
        if (simpleFeature.getAttributeCount() > 0) {
            // 获取 Feature 的属性
            Geometry geometry = (Geometry) simpleFeature.getDefaultGeometryProperty().getValue();
            
            Coordinate[] coordinates = geometry.getCoordinates();
            result.addAll(Arrays.asList(coordinates));
            return result;
        }
        return result;
    }
    
    /**
     * 转换坐标系
     *
     * @param geometry            原始几何体
     * @param coordinateTransform 坐标转换器，例如：CoordinateTransformationEnum.GCJ02_TO_WGS84
     * @return 转换后的几何体
     */
    public static Geometry transform(Geometry geometry, CoordinateTransform coordinateTransform) {
        // 创建转换器
        GeometryTransformer transformer = new CoordinateTransformer(coordinateTransform);
        return transformer.transform(geometry);
    }
    
    /**
     * 根据一个点和半径生成圆形
     *
     * @param center         中心点
     * @param radiusInMeters 半径（单位：米）
     * @param sourceCRS      坐标系，例如：DefaultGeographicCRS.WGS84
     * @return 圆形多边形
     */
    public static Polygon createProjectedCircle(Point center, double radiusInMeters, CoordinateReferenceSystem sourceCRS) throws Exception {
        // 自动选择合适的 UTM 投影（基于中心点经度）
        CoordinateReferenceSystem targetCRS = CRS.decode("AUTO:42001," + center.getX() + "," + center.getY());
        
        // 创建转换器：坐标系 -> UTM
        MathTransform toTransform = CRS.findMathTransform(sourceCRS, targetCRS, true);
        MathTransform fromTransform = CRS.findMathTransform(targetCRS, sourceCRS, true);
        
        // 转换点到 UTM 坐标系
        Point utmPoint = (Point) JTS.transform(center, toTransform);
        
        // 在 UTM 坐标系下创建缓冲区（单位：米）
        Polygon utmCircle = (Polygon) utmPoint.buffer(radiusInMeters);
        
        // 转换回 坐标系
        return (Polygon) JTS.transform(utmCircle, fromTransform);
    }
    
    /**
     * 判断点是否在几何图形的区域内
     *
     * @param outer 外部几何图形对象
     * @param inner 点位
     * @return 如果点在区域内返回true，否则返回false
     */
    @SneakyThrows
    public static boolean contains(SimpleFeature outer, Point inner) {
        Geometry geometry = (Geometry) outer.getDefaultGeometry();
        return geometry.contains(inner);
    }
    
    /**
     * 判断点是否在几何图形的区域内
     *
     * @param outer    外部几何图形
     * @param innerLon 经度
     * @param innerLat 纬度
     * @return 如果点在区域内返回true，否则返回false
     */
    @SneakyThrows
    public static boolean contains(SimpleFeature outer, double innerLon, double innerLat) {
        Point point = convertPoint(innerLon, innerLat);
        return contains(outer, point);
    }
    
    /**
     * 判断点是否在GeoJSON定义的区域内
     *
     * @param outer      外部几何图形对象列表
     * @param innerPoint 点位
     * @return 如果点在区域内返回true，否则返回false
     */
    @SneakyThrows
    public static boolean contains(SimpleFeatureCollection outer, Point innerPoint) {
        try (SimpleFeatureIterator iterator = outer.features()) {
            boolean containsAny = false;
            // 如果GeoJSON文件中包含多个几何形状（例如多个多边形），可以遍历所有要素并进行判断：
            while (iterator.hasNext()) {
                SimpleFeature feature = iterator.next();
                if (contains(feature, innerPoint)) {
                    containsAny = true;
                    break;
                }
            }
            return containsAny;
            
        }
    }
    
    /**
     * 判断点是否在GeoJSON定义的区域内
     *
     * @param outer    外部几何图形对象列表
     * @param innerLon 经度
     * @param innerLat 纬度
     * @return 如果点在区域内返回true，否则返回false
     */
    @SneakyThrows
    public static boolean contains(SimpleFeatureCollection outer, double innerLon, double innerLat) {
        Point point = convertPoint(innerLon, innerLat);
        return contains(outer, point);
    }
    
    /**
     * 判断坐标列表中是否在一个区域内，若有一个坐标在区域内，则返回true，否则返回false。
     *
     * @param outerGeoJsonText 外部 SimpleFeatureCollection json字符串 或者 SimpleFeature json字符串
     * @param innerLon         经度
     * @param innerLat         纬度
     * @return
     */
    public static boolean contains(String outerGeoJsonText, double innerLon, double innerLat) {
        SimpleFeatureCollection simpleFeatureCollection = convertSimpleFeatureCollection(outerGeoJsonText);
        if (!simpleFeatureCollection.isEmpty()) {
            return contains(simpleFeatureCollection, innerLon, innerLat);
        }
        
        SimpleFeature simpleFeature = convertFeature(outerGeoJsonText);
        if (simpleFeature.getAttributeCount() > 0) {
            return contains(simpleFeature, innerLon, innerLat);
        }
        
        return false;
    }
    
    /**
     * 判断坐标列表中是否在一个区域内，若有一个坐标在区域内，则返回true，否则返回false。
     *
     * @param outerGeoJsonText 外部 SimpleFeatureCollection json字符串 或者 SimpleFeature json字符串
     * @param innerCoordinates
     * @return
     */
    public static boolean contains(String outerGeoJsonText, Collection<Coordinate> innerCoordinates) {
        SimpleFeatureCollection simpleFeatureCollection = convertSimpleFeatureCollection(outerGeoJsonText);
        if (!simpleFeatureCollection.isEmpty()) {
            return innerCoordinates.stream()
                    .anyMatch(coordinate -> contains(simpleFeatureCollection, coordinate.getX(), coordinate.getY()));
        }
        
        SimpleFeature simpleFeature = convertFeature(outerGeoJsonText);
        if (simpleFeature.getAttributeCount() > 0) {
            return innerCoordinates.stream()
                    .anyMatch(coordinate -> contains(simpleFeature, coordinate.getX(), coordinate.getY()));
        }
        
        return false;
    }
    
    /**
     * 计算两点之间的距离
     *
     * @param lat1 纬度1
     * @param lon1 经度1
     * @param lat2 纬度2
     * @param lon2 经度2
     * @return 两点之间的距离。单位：米
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2, CoordinateReferenceSystem sourceCRS) {
        // 创建几何工厂
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        
        // 定义两个点的经纬度
        Point point1 = geometryFactory.createPoint(new Coordinate(lon1, lat1));
        Point point2 = geometryFactory.createPoint(new Coordinate(lon2, lat2));
        
        // 定义地理坐标系
        // 创建 GeodeticCalculator 实例
        GeodeticCalculator geodeticCalculator = new GeodeticCalculator(sourceCRS);
        // 设置起点
        geodeticCalculator.setStartingGeographicPoint(point1.getX(), point1.getY());
        // 设置终点
        geodeticCalculator.setDestinationGeographicPoint(point2.getX(), point2.getY());
        // 计算两点之间的距离（单位：米）
        return geodeticCalculator.getOrthodromicDistance();
    }
    
    /**
     * 计算速度
     *
     * @param prevLat       上一个点的纬度
     * @param prevLon       上一个点的经度
     * @param currLat       当前点的纬度
     * @param currLon       当前点的经度
     * @param prevTimestamp 上一个点的时间戳
     * @param currTimestamp 当前点的时间戳
     * @param sourceCRS     例如：DefaultGeographicCRS.WGS84
     * @return 速度。m/s
     */
    private static double calculateSpeed(double prevLat, double prevLon,
                                         double currLat, double currLon,
                                         long prevTimestamp, long currTimestamp,
                                         CoordinateReferenceSystem sourceCRS) {
        double distance = calculateDistance(prevLat, prevLon,
                currLat, currLon, sourceCRS);
        double timeDifference = (currTimestamp - prevTimestamp) / 1000.0;
        return distance / timeDifference;
    }
    
    /**
     * 判断是否停车
     *
     * @param prevLat           上一个点的纬度
     * @param prevLon           上一个点的经度
     * @param currLat           当前点的纬度
     * @param currLon           当前点的经度
     * @param distanceThreshold 距离阈值（米）
     * @return 是否停车
     */
    public static boolean checkIfVehicleIsParked(double prevLat, double prevLon,
                                                 double currLat, double currLon,
                                                 double distanceThreshold) {
        double distance = GeoUtils.calculateDistance(prevLat, prevLon, currLat, currLon, DefaultGeographicCRS.WGS84);
        return distance <= distanceThreshold;
    }
    
    /**
     * 获取随机坐标
     *
     * @param minLon
     * @param maxLon
     * @param minLat
     * @param maxLat
     * @return 坐标
     */
    public static Coordinate generateRandomCoordinate(double minLon, double maxLon, double minLat, double maxLat) {
        BigDecimal db = new BigDecimal(RandomUtil.randomDouble(0, 1) * (maxLon - minLon) + minLon);
        double lon = db.setScale(6, RoundingMode.HALF_UP).doubleValue();
        db = new BigDecimal(RandomUtil.randomDouble(0, 1) * (maxLat - minLat) + minLat);
        double lat = db.setScale(6, RoundingMode.HALF_UP).doubleValue();
        return new Coordinate(lon, lat);
    }
    
}
