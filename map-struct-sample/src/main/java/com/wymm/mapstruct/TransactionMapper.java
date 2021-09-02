package com.wymm.mapstruct;

import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Mapper
public abstract class TransactionMapper {
    
    /**
     * 在这里，我们为单个对象转换实现了完全自定义的映射方法
     */
    public TransactionDTO toTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUuid(transaction.getUuid());
        transactionDTO.setTotalInCents(transaction.getTotal()
                .multiply(new BigDecimal("100")).longValue());
        return transactionDTO;
    }
    
    /**
     * 另一方面，我们留下了用于将Collection映射到List抽象的方法，因此MapStruct将为我们实现它
     */
    public abstract List<TransactionDTO> toTransactionDTO(
            Collection<Transaction> transactions);
    
}
