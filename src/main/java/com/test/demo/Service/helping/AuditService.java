package com.test.demo.Service.helping;

import com.test.demo.DTO.Banking.WithdrawalDTO;
import com.test.demo.Enum.PaymentStatus;
import com.test.demo.Enum.TransactionType;
import com.test.demo.Repository.TransactionRepository;
import com.test.demo.models.Transaction;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AuditService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logFailedTransaction(User user, WithdrawalDTO withdrawalDTO, TransactionType type){
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTransactionType(type);
        transaction.setMessage("Transaction of amount " + withdrawalDTO.getAmount() + " was failed.");
        transaction.setRemainingBalance(user.getBalance());
        transaction.setPaymentStatus(PaymentStatus.FAILED);
        transaction.setAmount(withdrawalDTO.getAmount());
        transactionRepository.save(transaction);
        notificationService.sendTransactionAlert(
                user.getEmail(),
                "Transaction of amount " + withdrawalDTO.getAmount() +" was failed."
        );
    }

    public void saveSuccessTransaction(BigDecimal amount,
                                String message,
                                User user,
                                TransactionType transactionType,
                                PaymentStatus paymentStatus){
        Transaction transaction = new Transaction();
        transaction.setMessage(message);
        transaction.setAmount(amount);
        transaction.setUser(user);
        transaction.setTransactionType(transactionType);
        transaction.setRemainingBalance(user.getBalance());
        transaction.setPaymentStatus(paymentStatus);
        transactionRepository.save(transaction);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFailedTransaction(BigDecimal amount,
                                       String message,
                                       User user,
                                       TransactionType transactionType,
                                       PaymentStatus paymentStatus){
        Transaction transaction = new Transaction();
        transaction.setMessage(message);
        transaction.setAmount(amount);
        transaction.setUser(user);
        transaction.setTransactionType(transactionType);
        transaction.setRemainingBalance(user.getBalance());
        transaction.setPaymentStatus(paymentStatus);
        transactionRepository.save(transaction);

    }

}
