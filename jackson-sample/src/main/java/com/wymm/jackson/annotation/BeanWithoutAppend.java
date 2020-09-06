package com.wymm.jackson.annotation;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonAppend(
        attrs = {
                @JsonAppend.Attr("version")
        }
)
public class BeanWithoutAppend {
    private int id;
    private String name;
}
