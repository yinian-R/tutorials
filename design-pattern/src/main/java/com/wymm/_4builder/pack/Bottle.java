package com.wymm._4builder.pack;

public class Bottle implements Packing {
    @Override
    public String pack() {
        return "Bottle";
    }
    
    @Override
    public String toString() {
        return pack();
    }
}
