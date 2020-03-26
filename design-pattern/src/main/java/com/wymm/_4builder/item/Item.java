package com.wymm._4builder.item;

import com.wymm._4builder.pack.Packing;

public interface Item {
    String name();
    
    Packing packing();
    
    float price();
}
