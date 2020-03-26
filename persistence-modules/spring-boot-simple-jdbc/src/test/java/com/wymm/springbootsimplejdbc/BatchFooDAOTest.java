package com.wymm.springbootsimplejdbc;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BatchFooDAOTest {
    
    @Test
    void batchInsetData() {
        List<Foo> list100 = new ArrayList<>();
        list100.add(Foo.builder().bar("b-100").build());
        list100.add(Foo.builder().bar("b-101").build());
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(list100);
        System.out.println("1");
    }
}