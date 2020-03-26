package com.wymm._15command;

public class DemoMain {
    public static void main(String[] args) {
        Stock stock = new Stock();
        BuyStock buyStock = new BuyStock(stock);
        SellStock sellStock = new SellStock(stock);
        
        Broker broker = new Broker();
        broker.takeOrders(buyStock);
        broker.takeOrders(sellStock);
        
        
        broker.placeOrder();
    }
}
