package com.uber.UberApp.entities;

import com.uber.UberApp.entities.enums.TransactionMethod;
import com.uber.UberApp.entities.enums.TransactionType;

import java.time.LocalDateTime;

public class WalletTransaction {


    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private Ride ride;

    private String transactionId;

    private LocalDateTime timeStamp;

    private Wallet wallet;
}
