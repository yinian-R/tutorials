package com.wymm._13proxy;

public class RealImage implements Image {
    
    private String fileName;
    
    public RealImage(String fileName) {
        this.fileName = fileName;
        this.loadFileFromDisk(fileName);
    }
    
    private void loadFileFromDisk(String fileName) {
        System.out.println("Loading:" + fileName);
    }
    
    @Override
    public void display() {
        System.out.println("Display:" + fileName);
    }
    
}
