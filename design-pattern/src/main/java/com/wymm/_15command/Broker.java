package com.wymm._15command;

import java.util.ArrayList;
import java.util.List;

public class Broker {
    
    private List<Order> orderList = new ArrayList<>();
    
    public void takeOrders(Order order) {
        orderList.add(order);
    }
    
    public void placeOrder() {
        for (Order order : orderList) {
            order.execute();
        }
    }
}
