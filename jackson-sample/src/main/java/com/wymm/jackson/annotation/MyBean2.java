package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonPropertyOrder({"name", "id"})
public class MyBean2 {
    public int id;
    public String name;
}