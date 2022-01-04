package com.wymm.multipledb.web.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Order {
    
    private Long id;

    private Date orderTime;

    private Integer total;

    private User user;
    
}