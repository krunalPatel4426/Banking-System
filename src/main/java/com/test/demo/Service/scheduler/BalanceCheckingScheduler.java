package com.test.demo.Service.scheduler;

import com.test.demo.Repository.UserRepository;
import com.test.demo.Service.Banking.BankingService;
import com.test.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BalanceCheckingScheduler {

    private final BigDecimal minimumLimitForAccount = new BigDecimal(500);
    private final BigDecimal chargeForLessAmount = new BigDecimal(50);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankingService bankingService;

    @Scheduled(cron = "0 0 * * * *")
    public void autoDeductMoneyAndSendMail(){
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<User> userList = userRepository.findUserByBalanceIsLessThanEqualAndIsDeleted(minimumLimitForAccount, 0);
        for(User user : userList){
            if(user.getLastAutoDeduction() == null || user.getLastAutoDeduction().isBefore(oneMonthAgo)){
                bankingService.autoDeductMoneyAndSendMail(
                user,
                minimumLimitForAccount,
                chargeForLessAmount
                );
            }
        }
    }
}

