package com.wymm.jackson._enum;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Distance2 {
    KILOMETER("km", 1000),
    MILE("miles", 1609.34),
    METER("meters", 1),
    INCH("inches", 0.0254),
    CENTIMETER("cm", 0.01),
    MILLIMETER("mm", 0.001);
    
    private String unit;
    private double meters;
    
    Distance2(String unit, double meters) {
        this.unit = unit;
        this.meters = meters;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public double getMeters() {
        return meters;
    }
    
}
