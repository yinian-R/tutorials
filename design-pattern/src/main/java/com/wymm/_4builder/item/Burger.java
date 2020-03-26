package com.wymm._4builder.item;

import com.wymm._4builder.pack.Packing;
import com.wymm._4builder.pack.Wrapper;

public abstract class Burger implements Item {
    @Override
    public Packing packing() {
        return new Wrapper();
    }
    
    @Override
    public abstract float price();
}
