package com.wymm.multipledb.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    
    private Long id;
    @TableField(value = "first_name")
    private String firstName;
    @TableField(value = "last_name")
    private String lastName;
    private String email;
    private String age;
}
