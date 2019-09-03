package com.wymm.idea.builder;


import com.wymm.idea.annotation.Select;

public class MapperBuilder {
    
    @Select("select * from table")
    public void select() {
        System.out.println("run MapperBuilder#select");
    }
    
    public void insert() {
        System.out.println("run MapperBuilder#insert");
    }
    
    public void delete() {
        System.out.println("run MapperBuilder#delete");
    }
}
