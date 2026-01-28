package com.test.demo.models;

import com.test.demo.Enum.TransactionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="users")
@Builder
public class User {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Getter
    @Setter
    private String username;

    @Column(name="is_deleted")
    @Getter
    @Setter
    private int isDeleted = 0;


    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String password;

    @Column
    @Setter
    @Getter
    private BigDecimal balance;

    @Version
    private Long version;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Getter
    private List<Transaction> transactions;

    @Getter @Setter
    private LocalDateTime lastAutoDeduction;

    @Getter
    private String role = "ROLE_USER";

    @Getter
    @Setter
    private int isLocked = 0;


    @CreationTimestamp
    @Getter
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Getter
    @Column
    private LocalDateTime updatedAt;

    public User() {

    }
}

