package com.wymm.jackson.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SimpleBeanForCapitalizedFields {
    @JsonProperty("X")
    private int x = 1;
    private int y = 2;
}
