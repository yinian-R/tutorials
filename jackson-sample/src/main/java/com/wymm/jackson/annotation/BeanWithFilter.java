package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("myFilter")
public class BeanWithFilter {
    public int id;
    public String name;
}