package com.wymm.jackson.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Hidable{
    private String city;
    private String country;
    private boolean hidden;
}
