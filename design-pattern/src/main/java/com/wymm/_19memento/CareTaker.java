package com.wymm._19memento;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理者，负责管理备忘录 Memento ，不能对备忘录的状态进行访问和操作
 */
public class CareTaker {
    private Map<Integer, Memento> mementoList = new HashMap<>();
    
    public void add(Integer id, Memento state) {
        mementoList.put(id, state);
    }
    
    public Memento get(Integer id) {
        return mementoList.get(id);
    }
}
