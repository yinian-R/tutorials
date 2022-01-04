package com.wymm.coreenum.converter;

import java.util.stream.Stream;

public enum DaysOfWeekEnum {
    SUNDAY("off"),
    MONDAY("working"),
    TUESDAY("working"),
    WEDNESDAY("working"),
    THURSDAY("working"),
    FRIDAY("working"),
    SATURDAY("off");
    
    private final String typeOfDay;
    
    DaysOfWeekEnum(String typeOfDay) {
        this.typeOfDay = typeOfDay;
    }
    
    public String getTypeOfDay() {
        return typeOfDay;
    }
    
    public static Stream<DaysOfWeekEnum> stream() {
        return Stream.of(DaysOfWeekEnum.values());
    }
}