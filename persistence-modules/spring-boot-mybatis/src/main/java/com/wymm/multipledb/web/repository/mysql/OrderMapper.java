package com.wymm.multipledb.web.repository.mysql;

import com.wymm.multipledb.web.model.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<Order> finds();
    
}