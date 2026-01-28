package com.test.demo.models;

import com.test.demo.Enum.PaymentStatus;
import com.test.demo.Enum.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter @Setter
    private BigDecimal amount;

    @Getter @Setter
    private String message;

    @Getter @Setter
    private BigDecimal remainingBalance;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Setter @Getter
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @CreationTimestamp
    @Getter
    private LocalDateTime date;
}
