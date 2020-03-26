package com.wymm._15command;

public class SellStock implements Order {
    private Stock sellStock;
    
    public SellStock(Stock sellStock) {
        this.sellStock = sellStock;
    }
    
    @Override
    public void execute() {
        sellStock.sell();
    }
}
