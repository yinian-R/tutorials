package com.wymm._7bridge.b;

import com.wymm._7bridge.a.DrawAPI;

public abstract class Shape {
    protected DrawAPI drawAPI;
    
    protected Shape(DrawAPI drawAPI) {
        this.drawAPI = drawAPI;
    }
    
    public abstract void draw();
}
