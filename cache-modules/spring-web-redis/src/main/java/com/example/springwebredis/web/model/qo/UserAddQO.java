package com.example.springwebredis.web.model.qo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAddQO {
    
    private String name;
    
    private BigDecimal age;
}
