package com.wymm.multipledb.web.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String birthday;
}
