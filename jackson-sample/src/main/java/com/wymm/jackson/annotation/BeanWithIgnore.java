package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id"})
public class BeanWithIgnore {
    public int id;
    public String name;
}
