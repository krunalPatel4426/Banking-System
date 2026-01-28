package com.test.demo.Repository;

import com.test.demo.DTO.Transactions.TransactionSummary;
import com.test.demo.DTO.Transactions.TransactionsDTO;
import com.test.demo.models.Transaction;
import com.test.demo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t.id as id," +
            " t.message as message," +
            " t.paymentStatus as paymentStatus," +
            " t.transactionType as transactionType," +
            " t.amount as amount," +
            " t.remainingBalance as remainingBalance," +
            " t.date as date " +
            "from Transaction t where t.user = :user")
    Page<TransactionSummary> findTransactionsByUserId(User user, Pageable pageable);



    @Query(value =
            "select " +
            "t.id as id," +
            " t.message as message, " +
            "t.payment_status as paymentStatus, " +
            "t.transaction_type as transactionType, " +
            "t.amount as amount," +
            "t.remaining_balance as remainingBalance, " +
            "t.date as date" +
            " from transaction t " +
            "where t.user_id = :id " +
            "AND " +
            "t.date between :fromDate AND :toDate"
            , nativeQuery = true)
    Page<TransactionSummary> findTransactionsByUserId(Long id, LocalDateTime toDate, LocalDateTime fromDate, Pageable pageable);
}
