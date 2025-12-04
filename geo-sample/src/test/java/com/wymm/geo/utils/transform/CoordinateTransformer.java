package com.wymm.geo.utils.transform;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.util.GeometryTransformer;

public class CoordinateTransformer extends GeometryTransformer {
    
    private final CoordinateTransform coordinateTransform;
    
    public CoordinateTransformer(CoordinateTransform coordinateTransform) {
        this.coordinateTransform = coordinateTransform;
    }
    
    @Override
    protected CoordinateSequence transformCoordinates(
            CoordinateSequence coords, Geometry parent) {
        
        // 创建新的坐标序列，与原序列相同类型
        CoordinateSequence newCoords = createCoordinateSequence(coords.toCoordinateArray());
        
        // 转换每个坐标点
        for (int i = 0; i < coords.size(); i++) {
            Coordinate coord = coords.getCoordinate(i);
            Coordinate newCoord = coordinateTransform.transform(coord);
            newCoords.setOrdinate(i, 0, newCoord.x);
            newCoords.setOrdinate(i, 1, newCoord.y);
            // 保留Z值
            if (coords.getDimension() > 2 && !Double.isNaN(coord.z)) {
                newCoords.setOrdinate(i, 2, coord.z);
            }
        }
        
        return newCoords;
    }
    
}
