package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonPropertyOrder(alphabetic = true)
public class MyBean3 {
    public int id;
    public String name;
}