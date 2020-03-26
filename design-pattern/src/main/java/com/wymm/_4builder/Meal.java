package com.wymm._4builder;

import com.wymm._4builder.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private List<Item> items = new ArrayList<>();
    
    public void addItem(Item item) {
        items.add(item);
    }
    
    public float cost() {
        return (float) items.stream().mapToDouble(Item::price).sum();
    }
    
    public void showItems() {
        for (Item item : items) {
            System.out.print("Item: " + item.name());
            System.out.print("，Packing : " + item.packing());
            System.out.println("，Price : " + item.price());
        }
    }
}
