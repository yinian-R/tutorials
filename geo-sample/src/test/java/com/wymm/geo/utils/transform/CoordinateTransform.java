package com.wymm.geo.utils.transform;

import org.locationtech.jts.geom.Coordinate;

public interface  CoordinateTransform {
    
    Coordinate transform(Coordinate coord);
    
    
}
