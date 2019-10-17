package com.wymm.springbootsimplejdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSimpleJdbcApplication implements CommandLineRunner {
    
    @Autowired
    private FooDAO fooDAO;
    
    @Autowired
    private BatchFooDAO batchFooDAO;
    
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSimpleJdbcApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        //fooDAO.insertData();
        batchFooDAO.batchInsetData();
        fooDAO.listData();
    }
}
