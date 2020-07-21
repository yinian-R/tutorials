package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class AliasBean {
    @JsonAlias({"fName", "f_name"})
    private String firstName;
    private String lastName;
}
