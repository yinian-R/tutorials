package com.wymm.mapstruct.simple;

import lombok.Data;

@Data
public class TransactionDTO {
    private String uuid;
    private Long totalInCents;
}
