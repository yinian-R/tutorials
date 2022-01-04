package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("user")
public class UserWithRoot {
    public int id;
    public String name;
}