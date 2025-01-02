package com.codingshuttle.project.uber.uberApp.strategies.impl;

import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.Payment;
import com.codingshuttle.project.uber.uberApp.entities.Rider;
import com.codingshuttle.project.uber.uberApp.entities.enums.PaymentStatus;
import com.codingshuttle.project.uber.uberApp.entities.enums.TransactionMethod;
import com.codingshuttle.project.uber.uberApp.repositories.PaymentRepository;
import com.codingshuttle.project.uber.uberApp.services.PaymentService;
import com.codingshuttle.project.uber.uberApp.services.WalletService;
import com.codingshuttle.project.uber.uberApp.strategies.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


// -> Rider had 250 and Driver had 500
// -> Ride cost  -> Ride cost 100
// -> Rider -> 250-100
// -> Driver -> 500 + (100-30) = 570



@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;




    @Override
    @Transactional
    public void processPayment(Payment payment) {

        Driver driver=payment.getRide().getDriver();
        Rider rider=payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(),null,payment.getRide(), TransactionMethod.RIDE);

        double driverCut=payment.getAmount()*(1-PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(), driverCut, null,payment.getRide(),TransactionMethod.RIDE);

//        paymentService.updatePaymentStatus(payment, PaymentStatus.CONFIRMED);

        /*Error:
┌─────┐
|  paymentServiceImpl defined in file [F:\SpringBoot Projects\SpringBoot-Apps\UberAppMain\target\classes\com\
↑     ↓
|  paymentStrategyManager defined in file [F:\SpringBoot Projects\SpringBoot-Apps\UberAppMain\target\classes\com\
↑     ↓
|  cashPaymentStrategy defined in file [F:\SpringBoot Projects\SpringBoot-Apps\UberAppMain\target\classes\com\
└─────┘
         */

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
