package com.wymm.mapstruct;

import lombok.Data;

@Data
public class TransactionDTO {
    private String uuid;
    private Long totalInCents;
}
