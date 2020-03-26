package com.wymm._4builder.pack;

public class Wrapper implements Packing {
    @Override
    public String pack() {
        return "Wrapper";
    }
    
    @Override
    public String toString() {
        return pack();
    }
}
