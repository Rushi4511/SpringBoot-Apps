package com.uber.UberApp.entities;

import com.uber.UberApp.entities.enums.PaymentMethod;
import com.uber.UberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickUpLocation;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropLocation;

    @CreationTimestamp
    private LocalDateTime createdTime;  // Ride Created Time

    @ManyToOne(fetch= FetchType.LAZY)
    private Rider rider;

    @ManyToOne(fetch= FetchType.LAZY)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private Double fare;

    private String otp;

    private LocalDateTime startAT;  //After Entering OTP

    private LocalDateTime endedAt;  //Time at which Ride gets Completed
}
