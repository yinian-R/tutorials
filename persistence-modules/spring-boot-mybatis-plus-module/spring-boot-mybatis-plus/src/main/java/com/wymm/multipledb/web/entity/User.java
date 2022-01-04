package com.wymm.multipledb.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    
    private Long id;
    @TableField(value = "first_name")
    private String first_name;
    @TableField(value = "last_name")
    private String last_name;
    private String email;
    private String age;
}
