package com.wymm._19memento;

public class MainDemo {
    public static void main(String[] args) {
        
        Originator originator = new Originator(1, "janet", "state 1");
        
        CareTaker careTaker = new CareTaker();
        Integer id = originator.getId();
        careTaker.add(id, originator.saveStateToMemento());
        
        originator.setState("state 2");
        System.out.println("current State:" + originator.getState());
        
        originator.recoveryStateToMemento(careTaker.get(id));
        System.out.println("recovery State:" + originator.getState());
        
    }
    
}
