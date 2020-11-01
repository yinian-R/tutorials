package com.wymm.jackson.optional;

import lombok.Data;

import java.util.Optional;

@Data
public class Book {
    private String title;
    private Optional<String> subTitle;
}
