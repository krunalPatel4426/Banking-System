package com.test.demo.DTO.MoneyTransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyRequestDTO {
    @Getter  @Setter
    private Long fromId;

    @Getter @Setter
    private Long toId;

    @Getter @Setter
    private BigDecimal amount;

}
