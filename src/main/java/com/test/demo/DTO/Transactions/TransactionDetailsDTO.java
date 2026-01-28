package com.test.demo.DTO.Transactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsDTO {
    @Getter @Setter
    private String message;

    @Getter @Setter
    private BigDecimal newBalance;

    @Getter @Setter
    private Long transactionId;
}
