package com.test.demo.DTO.Banking;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class WithdrawalDTO {
    @Getter
    @Setter
    private Long id;
    @Getter @Setter
    private BigDecimal amount;

}
