package com.test.demo.DTO.Transactions;

import com.test.demo.Enum.PaymentStatus;
import com.test.demo.Enum.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionsDTO {
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String message;

    @Getter @Setter
    private PaymentStatus paymentStatus;

    @Getter @Setter
    private TransactionType transactionType;

    @Getter @Setter
    private BigDecimal amount;

    @Getter @Setter
    private BigDecimal remainingBalance;

    @Getter @Setter
    private LocalDateTime date;
}
