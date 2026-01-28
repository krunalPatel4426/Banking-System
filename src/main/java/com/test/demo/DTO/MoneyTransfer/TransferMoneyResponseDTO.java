package com.test.demo.DTO.MoneyTransfer;

import com.test.demo.Enum.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class TransferMoneyResponseDTO {
    @Getter @Setter
    private String message;

    @Getter @Setter
    private BigDecimal remainingBalance;

    @Getter @Setter
    private PaymentStatus paymentStatus;
}
