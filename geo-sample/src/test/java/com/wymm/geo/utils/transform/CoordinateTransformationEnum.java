package com.wymm.geo.utils.transform;

import cn.hutool.core.util.CoordinateUtil;
import org.locationtech.jts.geom.Coordinate;

/**
 * WGS84坐标系：即地球坐标系，中国外谷歌地图
 * GCJ02坐标系：即火星坐标系，高德、腾讯、阿里等使用
 * BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系。百度、搜狗等使用
 */
public enum CoordinateTransformationEnum implements CoordinateTransform {
    
    //----------------------------------------------------------------------------------- WGS84
    
    /**
     * WGS84 转换为 火星坐标系 (GCJ-02)
     */
    WGS84_TO_GCJ02 {
        @Override
        public Coordinate transform(Coordinate coord) {
            CoordinateUtil.Coordinate tempCoor = CoordinateUtil.wgs84ToGcj02(coord.getX(), coord.getY());
            return new Coordinate(tempCoor.getLng(), tempCoor.getLat());
        }
    },
    /**
     * WGS84 坐标转为 百度坐标系 (BD-09) 坐标
     */
    WGS84_TO_BD09 {
        @Override
        public Coordinate transform(Coordinate coord) {
            CoordinateUtil.Coordinate tempCoor = CoordinateUtil.wgs84ToBd09(coord.getX(), coord.getY());
            return new Coordinate(tempCoor.getLng(), tempCoor.getLat());
        }
    },
    
    //----------------------------------------------------------------------------------- GCJ-02
    
    /**
     * 火星坐标系 (GCJ-02) 转换为 WGS84
     */
    GCJ02_TO_WGS84 {
        @Override
        public Coordinate transform(Coordinate coord) {
            CoordinateUtil.Coordinate tempCoor = CoordinateUtil.gcj02ToWgs84(coord.getX(), coord.getY());
            return new Coordinate(tempCoor.getLng(), tempCoor.getLat());
        }
    },
    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
     */
    GCJ02_TO_BD09 {
        @Override
        public Coordinate transform(Coordinate coord) {
            CoordinateUtil.Coordinate tempCoor = CoordinateUtil.gcj02ToBd09(coord.getX(), coord.getY());
            return new Coordinate(tempCoor.getLng(), tempCoor.getLat());
        }
    },
    
    //----------------------------------------------------------------------------------- BD-09
    
    /**
     * 百度坐标系 (BD-09) 与 WGS84 的转换
     */
    BD09_TO_WGS84 {
        @Override
        public Coordinate transform(Coordinate coord) {
            CoordinateUtil.Coordinate tempCoor = CoordinateUtil.bd09toWgs84(coord.getX(), coord.getY());
            return new Coordinate(tempCoor.getLng(), tempCoor.getLat());
        }
    },
    /**
     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02) 的转换 即 百度 转 谷歌、高德
     */
    BD09_TO_GCJ02 {
        @Override
        public Coordinate transform(Coordinate coord) {
            CoordinateUtil.Coordinate tempCoor = CoordinateUtil.bd09ToGcj02(coord.getX(), coord.getY());
            return new Coordinate(tempCoor.getLng(), tempCoor.getLat());
        }
    },
    ;
}
