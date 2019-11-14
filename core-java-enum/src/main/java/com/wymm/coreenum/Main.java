package com.wymm.coreenum;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String convert = SimpleEnumConverter.CSYS.convert(1);
        System.out.println(convert);
    
        convert = SimpleEnumConverter.CSYS.convert(2);
        System.out.println(convert);


        Thread.sleep(2000);
        convert = SimpleEnumConverter.CSYS.convert(3);
        System.out.println(convert);
    
    
        convert = SimpleEnumConverter.VEHICLE_TYPE.convert(1);
        System.out.println(convert);
        
        SimpleEnumConverter.CSYS.ma
    }
}
