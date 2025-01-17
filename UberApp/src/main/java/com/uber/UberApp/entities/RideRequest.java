package com.uber.UberApp.entities;

import com.uber.UberApp.dto.RiderDto;
import com.uber.UberApp.entities.enums.PaymentMethod;
import com.uber.UberApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;


    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickUpLocation;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropOfLocation;

    @CreationTimestamp
    private LocalDateTime requestTime;

    private double fare;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;


    @Enumerated(EnumType.STRING)
    private RideRequestStatus rideRequestStatus;


}
