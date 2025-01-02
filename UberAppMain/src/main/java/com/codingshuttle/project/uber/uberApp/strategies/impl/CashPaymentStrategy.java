package com.codingshuttle.project.uber.uberApp.strategies.impl;

import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.Payment;
import com.codingshuttle.project.uber.uberApp.entities.Wallet;
import com.codingshuttle.project.uber.uberApp.entities.enums.PaymentStatus;
import com.codingshuttle.project.uber.uberApp.entities.enums.TransactionMethod;
import com.codingshuttle.project.uber.uberApp.repositories.PaymentRepository;
import com.codingshuttle.project.uber.uberApp.services.PaymentService;
import com.codingshuttle.project.uber.uberApp.services.WalletService;
import com.codingshuttle.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// -> Driver Amount 100
// -> Driver Amount 70 +30 Commission of platform


@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;


    @Override
    public void processPayment(Payment payment) {


        Driver driver= payment.getRide().getDriver();

        Wallet driverWallet = walletService.findByUser(driver.getUser());
        double platFormCommission =driverWallet.getBalance()*PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(),platFormCommission,null,payment.getRide(), TransactionMethod.CASH);

//        paymentService.updatePaymentStatus(payment,PaymentStatus.CONFIRMED);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}


//10 ratingCount -> 4.0
// new rating -> 4.6
//updating rating

//new rating 44.6/11 -> 4.05 Rating