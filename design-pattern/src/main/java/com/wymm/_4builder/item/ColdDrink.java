package com.wymm._4builder.item;

import com.wymm._4builder.pack.Bottle;
import com.wymm._4builder.pack.Packing;

public abstract class ColdDrink implements Item {
    @Override
    public Packing packing() {
        return new Bottle();
    }
    
    @Override
    public abstract float price();
}
