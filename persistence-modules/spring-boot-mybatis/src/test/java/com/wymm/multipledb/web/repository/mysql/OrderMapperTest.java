package com.wymm.multipledb.web.repository.mysql;

import com.wymm.multipledb.web.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class OrderMapperTest {
    
    @Resource
    private OrderMapper orderMapper;
    
    /**
     * 一对一查询
     */
    @Test
    void finds() {
        List<Order> list = orderMapper.finds();
        System.out.println();
    }
}