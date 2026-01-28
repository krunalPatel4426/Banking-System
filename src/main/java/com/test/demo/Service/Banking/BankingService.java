package com.test.demo.Service.Banking;

import com.test.demo.DTO.Banking.WithdrawalDTO;
import com.test.demo.DTO.MoneyTransfer.TransferMoneyRequestDTO;
import com.test.demo.DTO.MoneyTransfer.TransferMoneyResponseDTO;
import com.test.demo.DTO.Transactions.TransactionDetailsDTO;
import com.test.demo.DTO.Transactions.TransactionSummary;
import com.test.demo.DTO.Transactions.TransactionsDTO;
import com.test.demo.Enum.PaymentStatus;
import com.test.demo.Enum.TransactionType;
import com.test.demo.Repository.TransactionRepository;
import com.test.demo.Repository.UserRepository;
import com.test.demo.Service.helping.NotificationService;
import com.test.demo.Service.helping.AuditService;
import com.test.demo.config.CustomError.AccountLockedException;
import com.test.demo.config.CustomError.InsufficientBalance;
import com.test.demo.config.CustomError.UserNotFoundException;
import com.test.demo.models.Transaction;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class BankingService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 2,
            backoff = @Backoff(delay = 50)
    )
    public ResponseEntity<TransactionDetailsDTO> withdrawalService(WithdrawalDTO withdrawalDTO) {
        User user = userRepository.findUserByIdAndIsDeleted(withdrawalDTO.getId(), 0).orElseThrow(() -> new UserNotFoundException("User not found."));
        if(user.getIsLocked() == 1){
            throw new AccountLockedException("Your account was freezed. Please Contact Branch near by you.");
        }
        if(withdrawalDTO.getAmount().compareTo(user.getBalance()) > 0){
            auditService.logFailedTransaction(user, withdrawalDTO, TransactionType.DEBITED);
            throw new InsufficientBalance("Insufficient Balance. Please Check Your balance.");
        }
        user.setBalance(user.getBalance().subtract(withdrawalDTO.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(withdrawalDTO.getAmount());
        transaction.setTransactionType(TransactionType.DEBITED);
        transaction.setPaymentStatus(PaymentStatus.SUCCESS);
        transaction.setRemainingBalance(user.getBalance());
        transaction.setMessage("Debited by you.");
        transactionRepository.save(transaction);
        userRepository.save(user);
        TransactionDetailsDTO transactionDetailsDTO = new TransactionDetailsDTO(
                "Amount" + withdrawalDTO.getAmount() + "debited successfully.",
                transaction.getRemainingBalance(),
                transaction.getId()
        );
        notificationService.sendTransactionAlert(
                user.getEmail(),
                "Amount" + withdrawalDTO.getAmount() + " was debited."
        );
        return ResponseEntity.ok(transactionDetailsDTO);
    }


    @Transactional
    public ResponseEntity<TransactionDetailsDTO> creditService(WithdrawalDTO withdrawalDTO) {
        return credit(withdrawalDTO);
    }

    public ResponseEntity<TransactionDetailsDTO> credit(WithdrawalDTO withdrawalDTO){
        User user = userRepository.findUserByIdAndIsDeleted(withdrawalDTO.getId(), 0).orElseThrow(() -> new UserNotFoundException("User not found."));
        user.setBalance(user.getBalance().add(withdrawalDTO.getAmount()));
        if(user.getLastAutoDeduction() != null && (user.getBalance().compareTo(new BigDecimal(500)) > 0)){
            user.setLastAutoDeduction(null);
        }
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(withdrawalDTO.getAmount());
        transaction.setTransactionType(TransactionType.CREDITED);
        transaction.setMessage("Credited by you.");
        transaction.setPaymentStatus(PaymentStatus.SUCCESS);
        transaction.setRemainingBalance(user.getBalance());
        transactionRepository.save(transaction);
        userRepository.save(user);

        TransactionDetailsDTO transactionDetailsDTO = new TransactionDetailsDTO(
                "Amount " + withdrawalDTO.getAmount() + " credited successfully.", transaction.getRemainingBalance(), transaction.getId()
        );

        notificationService.sendTransactionAlert(
                user.getEmail(),
                "Amount " + withdrawalDTO.getAmount() + " was credit."
        );
        return ResponseEntity.ok(transactionDetailsDTO);
    }

    public ResponseEntity<Page<TransactionsDTO>> historyService(Long id, int page, int size, String sort) {
        return history(id, page, size, sort);
    }
    public ResponseEntity<Page<TransactionsDTO>> historyService(Long id, int page, int size, LocalDateTime toDate, LocalDateTime fromDate, String sort) {
        return history(id, page, size, toDate, fromDate, sort);
    }

    private ResponseEntity<Page<TransactionsDTO>> history(Long id, int page, int size, String sort){
        User user = userRepository.findUserByIdAndIsDeleted(id, 0).orElseThrow(() -> new UserNotFoundException("User not found."));
        Pageable pageable = switch (sort) {
            case "dateAsc" -> PageRequest.of(page, size, Sort.by("date"));
            case "amountDesc" -> PageRequest.of(page, size, Sort.by("amount").descending());
            case "amountAsc" -> PageRequest.of(page, size, Sort.by("amount"));
            default -> PageRequest.of(page, size, Sort.by("date").descending());
        };

        Page<TransactionSummary> rawTransactions = transactionRepository.findTransactionsByUserId(user, pageable);

        Page<TransactionsDTO> transactions = rawTransactions.map(
                t ->
                    new TransactionsDTO(
                            t.getId(),
                            t.getMessage(),
                            t.getPaymentStatus(),
                            t.getTransactionType(),
                            t.getAmount(),
                            t.getRemainingBalance(),
                            t.getDate()
                    )
        );
        return ResponseEntity.ok(transactions);
    }

    private ResponseEntity<Page<TransactionsDTO>> history(Long id, int page, int size, LocalDateTime toDate, LocalDateTime fromDate, String sort){
        Pageable pageable = switch (sort) {
            case "dateAsc" -> PageRequest.of(page, size, Sort.by("date"));
            case "amountDesc" -> PageRequest.of(page, size, Sort.by("amount").descending());
            case "amountAsc" -> PageRequest.of(page, size, Sort.by("amount"));
            default -> PageRequest.of(page, size, Sort.by("date").descending());
        };
        Page<TransactionSummary> rawTransactions = transactionRepository.findTransactionsByUserId(id, toDate, fromDate, pageable);

        Page<TransactionsDTO> transactions = rawTransactions.map(
                t ->
                        new TransactionsDTO(
                                t.getId(),
                                t.getMessage(),
                                t.getPaymentStatus(),
                                t.getTransactionType(),
                                t.getAmount(),
                                t.getRemainingBalance(),
                                t.getDate()
                        )
        );
        return ResponseEntity.ok(transactions);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void autoDeductMoneyAndSendMail(User user, BigDecimal minimumLimitForAccount, BigDecimal chargeForLessAmount){
        user.setBalance(user.getBalance().subtract(chargeForLessAmount));
        Transaction transaction = new Transaction();
        transaction.setMessage("Amount " + chargeForLessAmount + " was deducted because of your balance was below " + minimumLimitForAccount);
        transaction.setAmount(chargeForLessAmount);
        transaction.setTransactionType(TransactionType.DEBITED);
        transaction.setPaymentStatus(PaymentStatus.SUCCESS);
        transaction.setUser(user);
        transaction.setRemainingBalance(user.getBalance());
        user.setLastAutoDeduction(LocalDateTime.now());
        transactionRepository.save(transaction);
        userRepository.save(user);
        notificationService.sendDeductionMailByScheduler(
                user.getEmail(),
                "Amount " + chargeForLessAmount + " was deducted from your account because amount was less then 500."
        );
    }

    @Transactional
    public ResponseEntity<TransferMoneyResponseDTO> transferMoneyService(TransferMoneyRequestDTO transferMoneyRequestDTO) {
        System.out.println(
                "working"
        );
        if(transferMoneyRequestDTO.getToId().equals(transferMoneyRequestDTO.getFromId())){
            throw new IllegalArgumentException("Self-transfer is not allowed.");
        }
        if(transferMoneyRequestDTO.getToId() < transferMoneyRequestDTO.getFromId()){
            User fromUser = userRepository.findByIdWithLock(transferMoneyRequestDTO.getFromId()).orElseThrow(() -> new UserNotFoundException("User with id : " + transferMoneyRequestDTO.getFromId() + " not found please check your id first."));
            User toUser = userRepository.findByIdWithLock(transferMoneyRequestDTO.getToId()).orElseThrow(() -> new UserNotFoundException("User with id : " + transferMoneyRequestDTO.getToId() + " not found please check it again."));
            return transferMoney(transferMoneyRequestDTO.getAmount() ,fromUser, toUser);
        }else{
            System.out.println("Working");
            User toUser = userRepository.findByIdWithLock(transferMoneyRequestDTO.getToId()).orElseThrow(() -> new UserNotFoundException("User with id : " + transferMoneyRequestDTO.getToId() + " not found please check it again."));
            User fromUser = userRepository.findByIdWithLock(transferMoneyRequestDTO.getFromId()).orElseThrow(() -> new UserNotFoundException("User with id : " + transferMoneyRequestDTO.getFromId() + " not found please check your id first."));
            return transferMoney(transferMoneyRequestDTO.getAmount() ,fromUser, toUser);
        }
    }

    private ResponseEntity<TransferMoneyResponseDTO> transferMoney(BigDecimal amount, User fromUser, User toUser){
        if(fromUser.getIsLocked() == 1){
            throw new AccountLockedException("Your account was freezed. Please Contact branch near by you.");
        }
        if(fromUser.getBalance().compareTo(amount) < 0){
            auditService.saveFailedTransaction(
                    amount,
                    "Transfer of amount " + amount + " was failed because you do not have enogh money.",
                    fromUser,
                    TransactionType.DEBITED,
                    PaymentStatus.FAILED
            );
            throw new InsufficientBalance("Insufficient Balance in your account. current balance is : " + fromUser.getBalance());
        }
        fromUser.setBalance(fromUser.getBalance().subtract(amount));
        toUser.setBalance(toUser.getBalance().add(amount));
        if(toUser.getLastAutoDeduction() != null && toUser.getBalance().compareTo(new BigDecimal(500)) > 0){
            toUser.setLastAutoDeduction(null);
        }
        auditService.saveSuccessTransaction(
                amount,
                "Amount " + amount + " was credited into " + toUser.getUsername() + "'s account.",
                fromUser,
                TransactionType.DEBITED,
                PaymentStatus.SUCCESS
        );
        auditService.saveSuccessTransaction(
                amount,
                "Amount " + amount + " was credited By " + fromUser.getUsername() + ".",
                toUser,
                TransactionType.CREDITED,
                PaymentStatus.SUCCESS
        );
        userRepository.save(fromUser);
        userRepository.save(toUser);
        TransferMoneyResponseDTO transferMoneyResponseDTO = new TransferMoneyResponseDTO();
        transferMoneyResponseDTO.setMessage("Amount " + amount + " was credited into " + toUser.getUsername() + "'s account.");
        transferMoneyResponseDTO.setPaymentStatus(PaymentStatus.SUCCESS);
        transferMoneyResponseDTO.setRemainingBalance(fromUser.getBalance());

        return ResponseEntity.ok(transferMoneyResponseDTO);
    }


}
