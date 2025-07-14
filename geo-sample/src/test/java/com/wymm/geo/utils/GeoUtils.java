package com.wymm.geo.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CoordinateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

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
    public static String wktToGeojson(String wkt) {
        if (ObjectUtils.isEmpty(wkt)) {
            return null;
        }
        // 创建 WKT 读取器
        WKTReader reader = new WKTReader(JTSFactoryFinder.getGeometryFactory());
        Geometry geometry = reader.read(wkt);
        
        return convertGeojsonString(geometry);
    }
    
    /**
     * WKT格式的几何数据 转换成 geojson
     *
     * @param wkt
     * @return geojson。格式：{"type":"Feature" ...}
     */
    @SneakyThrows
    public static String wktToFeatureJSON(String wkt) {
        if (ObjectUtils.isEmpty(wkt)) {
            return null;
        }
        // 创建几何工厂
        GeometryFactory geometryFactory = new GeometryFactory();
        
        // 使用 WKTReader 解析 WKT 字符串
        WKTReader wktReader = new WKTReader(geometryFactory);
        Geometry geometry = wktReader.read(wkt);
        
        // 创建一个简单的几何要素
        // 定义属性，这里仅包含一个几何属性
        SimpleFeatureType featureType = DataUtilities.createType("FeatureCollection", "the_geom:Geometry,name:String");
        
        // 创建一个包含几何要素的 SimpleFeature 对象
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(featureType);
        builder.add(geometry); // 添加几何属性
        SimpleFeature feature = builder.buildFeature(null); // 特征ID
        
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
    public static String wktToFeatureCollectionJSON(String wkt) {
        if (ObjectUtils.isEmpty(wkt)) {
            return null;
        }
        // 创建几何工厂
        GeometryFactory geometryFactory = new GeometryFactory();
        
        // 使用 WKTReader 解析 WKT 字符串
        WKTReader wktReader = new WKTReader(geometryFactory);
        Geometry geometry = wktReader.read(wkt);
        
        // 创建一个简单的几何要素
        // 定义属性，这里仅包含一个几何属性
        SimpleFeatureType featureType = DataUtilities.createType("FeatureCollection", "the_geom:Geometry,name:String");
        
        // 创建一个包含几何要素的 SimpleFeature 对象
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(featureType);
        builder.add(geometry); // 添加几何属性
        SimpleFeature feature = builder.buildFeature(null); // 特征ID
        
        // 创建要素集合
        SimpleFeatureCollection featureCollection = DataUtilities.collection(feature);
        
        // 将要素集合转换为 GeoJSON
        return convertGeojsonString(featureCollection);
    }
    
    /**
     * WKT格式的几何数据 转换成 geojson
     *
     * @param wkt
     * @return
     */
    @SneakyThrows
    public static Geometry wktToGeometry(String wkt) {
        WKTReader reader = new WKTReader();
        return reader.read(wkt);
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
    public static boolean isParking(double prevLat, double prevLon,
                                    double currLat, double currLon,
                                    double distanceThreshold) {
        double distance = GeoUtils.calculateDistance(prevLat, prevLon, currLat, currLon, DefaultGeographicCRS.WGS84);
        return distance <= distanceThreshold;
    }
    
    /**
     * 判断点是否在几何图形的区域内
     *
     * @param feature 几何图形对象
     * @param point   点位
     * @return 如果点在区域内返回true，否则返回false
     */
    @SneakyThrows
    public static boolean isPointInFeature(SimpleFeature feature, Point point) {
        Geometry geometry = (Geometry) feature.getDefaultGeometry();
        return geometry.contains(point);
    }
    
    /**
     * 判断点是否在几何图形的区域内
     *
     * @param feature
     * @param lon     经度
     * @param lat     纬度
     * @return 如果点在区域内返回true，否则返回false
     */
    @SneakyThrows
    public static boolean isPointInFeature(SimpleFeature feature, double lon, double lat) {
        Point point = convertPoint(lon, lat);
        return isPointInFeature(feature, point);
    }
    
    /**
     * 判断点是否在GeoJSON定义的区域内
     *
     * @param featureCollection 几何图形对象列表
     * @param point             点位
     * @return 如果点在区域内返回true，否则返回false
     */
    @SneakyThrows
    public static boolean isPointInFeatureCollection(SimpleFeatureCollection featureCollection, Point point) {
        try (SimpleFeatureIterator iterator = featureCollection.features()) {
            boolean isInsideAny = false;
            // 如果GeoJSON文件中包含多个几何形状（例如多个多边形），可以遍历所有要素并进行判断：
            while (iterator.hasNext()) {
                SimpleFeature feature = iterator.next();
                if (isPointInFeature(feature, point)) {
                    isInsideAny = true;
                    break;
                }
            }
            return isInsideAny;
            
        }
    }
    
    /**
     * 判断点是否在GeoJSON定义的区域内
     *
     * @param featureCollection 几何图形对象列表
     * @param lon               经度
     * @param lat               纬度
     * @return 如果点在区域内返回true，否则返回false
     */
    @SneakyThrows
    public static boolean isPointInFeatureCollection(SimpleFeatureCollection featureCollection, double lon, double lat) {
        Point point = convertPoint(lon, lat);
        return isPointInFeatureCollection(featureCollection, point);
    }
    
    /**
     * 判断坐标列表中是否在一个区域内，若有一个坐标在区域内，则返回true，否则返回false。
     *
     * @param geoJsonText SimpleFeatureCollection json字符串 或者 SimpleFeature json字符串
     * @param lon         经度
     * @param lat         纬度
     * @return
     */
    public static boolean isPointInGeoJSON(String geoJsonText, double lon, double lat) {
        SimpleFeatureCollection simpleFeatureCollection = convertSimpleFeatureCollection(geoJsonText);
        if (!simpleFeatureCollection.isEmpty()) {
            return isPointInFeatureCollection(simpleFeatureCollection, lon, lat);
        }
        
        SimpleFeature simpleFeature = convertFeature(geoJsonText);
        if (simpleFeature.getAttributeCount() > 0) {
            return isPointInFeature(simpleFeature, lon, lat);
        }
        
        return false;
    }
    
    /**
     * 判断坐标列表中是否在一个区域内，若有一个坐标在区域内，则返回true，否则返回false。
     *
     * @param geoJsonText SimpleFeatureCollection json字符串 或者 SimpleFeature json字符串
     * @param coordinates
     * @return
     */
    public static boolean isPointInGeoJSON(String geoJsonText, Collection<Coordinate> coordinates) {
        SimpleFeatureCollection simpleFeatureCollection = convertSimpleFeatureCollection(geoJsonText);
        if (!simpleFeatureCollection.isEmpty()) {
            return coordinates.stream()
                    .anyMatch(coordinate -> isPointInFeatureCollection(simpleFeatureCollection, coordinate.getX(), coordinate.getY()));
        }
        
        SimpleFeature simpleFeature = convertFeature(geoJsonText);
        if (simpleFeature.getAttributeCount() > 0) {
            return coordinates.stream()
                    .anyMatch(coordinate -> isPointInFeature(simpleFeature, coordinate.getX(), coordinate.getY()));
        }
        
        return false;
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
            FeatureJSON fjson = new FeatureJSON();
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
            FeatureJSON fjson = new FeatureJSON();
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
            GeometryJSON gjson = new GeometryJSON();
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
    public static LineString convertToLineString(Coordinate[] coordinates) {
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
    public static LineString convertToLineString(List<CoordinateUtil.Coordinate> list) {
        if (ObjectUtil.isNull(list)) {
            return null;
        }
        Coordinate[] coordinates = list.stream().map(item -> new Coordinate(item.getLng(), item.getLat())).toArray(Coordinate[]::new);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        return geometryFactory.createLineString(coordinates);
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
    public static Coordinate randomLonLat(double minLon, double maxLon, double minLat, double maxLat) {
        BigDecimal db = new BigDecimal(RandomUtil.randomDouble(0, 1) * (maxLon - minLon) + minLon);
        double lon = db.setScale(6, RoundingMode.HALF_UP).doubleValue();
        db = new BigDecimal(RandomUtil.randomDouble(0, 1) * (maxLat - minLat) + minLat);
        double lat = db.setScale(6, RoundingMode.HALF_UP).doubleValue();
        return new Coordinate(lon, lat);
    }
    
}
