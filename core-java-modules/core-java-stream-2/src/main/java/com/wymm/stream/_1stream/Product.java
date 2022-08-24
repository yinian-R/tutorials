package com.wymm.stream._1stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@Data
public class Product {
    
    private int price;
    
    private String name;
    
    private boolean utilize;
    
    public Product(int price, String name) {
        this.price = price;
        this.name = name;
    }
    
    public static Stream<String> streamOf(List<String> list) {
        return (list == null || list.isEmpty()) ? Stream.empty() : list.stream();
    }
    
}
