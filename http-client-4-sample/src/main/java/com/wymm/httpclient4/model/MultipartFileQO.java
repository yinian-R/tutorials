package com.wymm.httpclient4.model;

import lombok.Data;

@Data
public class MultipartFileQO {
    
    private String username;
    
    private MultipartFileQO file;
    private MultipartFileQO file1;
    private MultipartFileQO file2;
}
