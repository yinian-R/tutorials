package com.wymm._13proxy;

public class DemoMain {
    public static void main(String[] args) {
        Image image = new ProxyImage("test.png");
        image.display();
        System.out.println();
        image.display();
    }
}
