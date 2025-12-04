package com.wymm.geo;

import com.wymm.geo.utils.GeoUtils;
import lombok.SneakyThrows;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * 计算距离
 */
public class _2DistanceTest {
    
    /**
     * 计算两点之间的距离
     */
    @Test
    void distanceTest() {
        double distance = GeoUtils.calculateDistance(23.162214, 113.473985, 23.162347,113.473561,  DefaultGeographicCRS.WGS84);
        System.out.println(distance + "米");
    }
    
    /**
     * 判断特定经纬度在某块区域里面
     */
    @SneakyThrows
    @Test
    void geoContains() {
        // GeoJSON 文本
        String geojsonText = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[113.472349,23.16296],[113.47491,23.162624],[113.47418,23.16073],[113.471744,23.161496],[113.472349,23.16296]]]}}]}";
        
        
        // 待判断的经纬度点
        double longitude = 113.473304; // 示例：社区中心经度
        double latitude = 23.161979;   // 示例：社区中心纬度
        
        boolean isInside = GeoUtils.contains(geojsonText, longitude, latitude);
        if (isInside) {
            System.out.println("点位于区域内。");
        } else {
            System.out.println("点不在区域内。");
        }
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
    public static boolean isParking(double prevLat, double prevLon, double currLat, double currLon,
                                    double distanceThreshold) {
        double distance = GeoUtils.calculateDistance(prevLat, prevLon, currLat, currLon, DefaultGeographicCRS.WGS84);
        return distance <= distanceThreshold;
    }
    
    @Test
    void isParking() {
        double prevLat = 23.16296;
        double prevLon = 113.472349;
        double currLat = 23.162624;
        double currLon = 113.47491;
        double distanceThreshold = 5.0; // 5米
        double timeThreshold = 30.0; // 30秒
        double timeElapsed = 35.0; // 经过35秒
        
        boolean parked = isParking(prevLat, prevLon, currLat, currLon, distanceThreshold);
        System.out.println("车辆是否停车: " + parked);
        
        boolean isDuration = timeElapsed >= timeThreshold;
        System.out.println("车辆是否停车持续达到阈值: " + isDuration);
    }
    
    @Test
    void convert(){
        String geojsonText = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[113.472349,23.16296],[113.47491,23.162624],[113.47418,23.16073],[113.471744,23.161496],[113.472349,23.16296]]]}}]}";
        SimpleFeatureCollection featureCollection = GeoUtils.convertSimpleFeatureCollection(geojsonText);
        System.out.println(featureCollection);
        
        geojsonText = "{\"id\":\"9211bfed0a3c480d1074cf7972192785\",\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"coordinates\":[[[113.32256575029186,22.888155655710406],[113.3173945951446,22.889097375057332],[113.30651111745215,22.893695087441046],[113.30140009201716,22.895523050285476],[113.29532699120506,22.89724020506705],[113.29460543467286,22.89724020506705],[113.2941243969857,22.897794121328232],[113.28931402010534,22.899012729140225],[113.28708922079784,22.90012054492398],[113.28390234611447,22.90189303135817],[113.28119650911992,22.903222380982555],[113.27891158010141,22.90333315952948],[113.27770898588057,22.903056212991743],[113.27506327859675,22.90327777026731],[113.27410120322105,22.902723876400017],[113.272958738711,22.899178902085012],[113.27241757131304,22.898403426602997],[113.2725979604457,22.8972955967955],[113.27482275975319,22.89585540452032],[113.27548418657301,22.89596618908405],[113.27638613223945,22.89557844271438],[113.27770898588057,22.89480294665043],[113.27825015328006,22.89375048061622],[113.27993378518806,22.892642612813077],[113.28269975189522,22.890482244579488],[113.28672844253248,22.888266446560806],[113.2937636187188,22.883502358318395],[113.2954472506284,22.88250520240858],[113.29863412531171,22.88139724280738],[113.30121970288457,22.88184042773281],[113.30368502103562,22.882615997871852],[113.30482748554408,22.88339156357928],[113.30825487907254,22.884000933526224],[113.31258421826419,22.884776491321105],[113.32256575029186,22.888155655710406]]],\"type\":\"Polygon\"}}";
        boolean inArea = GeoUtils.contains(geojsonText, 113.2866641658, 22.8992521491);
        System.out.println(inArea);
        SimpleFeature simpleFeature = GeoUtils.convertFeature(geojsonText);
        SimpleFeatureType type = simpleFeature.getType();
        System.out.println(type.getTypeName());
        
        // type 具有的类型都是 Geometry 的子类名称
        Geometry defaultGeometry = (Geometry) simpleFeature.getDefaultGeometry();
        String geometryType = defaultGeometry.getGeometryType();
        System.out.println(geometryType);
        
        String targetGeojson = GeoUtils.convertGeojsonString(simpleFeature);
        System.out.println(targetGeojson);
        
        System.out.println();
    }
    
    /**
     * 根据一个点和半径生成圆形
     * @throws Exception
     */
    @Test
    void createProjectedCircle() throws Exception {
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        // 创建中心点（经度, 纬度）
        Point center = geometryFactory.createPoint(new Coordinate(116.4074, 39.9042)); // 北京
        
        // 生成半径为 1000 米的圆形
        Polygon circle = GeoUtils.createProjectedCircle(center, 1000, DefaultGeographicCRS.WGS84);
        
        // 输出圆形的 WKT 表示
        System.out.println(circle.toText());
        
        String geojson = GeoUtils.convertGeojsonString(circle);
        // 输出圆形的 geojson
        System.out.println(geojson);
    }
}
