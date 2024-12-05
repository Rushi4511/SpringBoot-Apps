package com.uber.UberApp.entities;

import com.uber.UberApp.entities.enums.PaymentMethod;
import com.uber.UberApp.entities.enums.PaymentStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToOne(fetch = FetchType.LAZY)
    private Ride ride;

    private Double amount;

    @CreationTimestamp
    private LocalDateTime paymentTime;
}
