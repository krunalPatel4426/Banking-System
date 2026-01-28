package com.test.demo.Service.helping;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Async
    public void sendTransactionAlert(String email, String message){
        try{
            System.out.println("[Thread - " + Thread.currentThread().getName() + "] Preparing email for : " + email);
            Thread.sleep(5000);
            System.out.println("[Thread - " + Thread.currentThread().getName() + "] Email SENT to : " + email + ":" + message);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    @Async
    public void sendDeductionMailByScheduler(String email, String message){
        try{
            System.out.println("[Thread - " + Thread.currentThread().getName() + "] Preparing email for : " + email);
            Thread.sleep(5000);
            System.out.println("[Thread - " + Thread.currentThread().getName() + "] Email send to : " + email + " : " + message );
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
