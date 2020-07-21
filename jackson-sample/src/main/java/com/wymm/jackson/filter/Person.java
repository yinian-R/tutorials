package com.wymm.jackson.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Hidable {
    private String name;
    private Address address;
    private boolean hidden;
}
