package com.test.demo.DTO.Transactions;

import com.test.demo.Enum.PaymentStatus;
import com.test.demo.Enum.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface TransactionSummary {
    Long getId();
    String getMessage();
    PaymentStatus getPaymentStatus();
    TransactionType getTransactionType();
    BigDecimal getAmount();
    BigDecimal getRemainingBalance();
    LocalDateTime getDate();
}
