package com.wymm._15command;

public class BuyStock implements Order {
    
    private Stock bugStock;
    
    public BuyStock(Stock bugStock) {
        this.bugStock = bugStock;
    }
    
    @Override
    public void execute() {
        bugStock.buy();
    }
}
