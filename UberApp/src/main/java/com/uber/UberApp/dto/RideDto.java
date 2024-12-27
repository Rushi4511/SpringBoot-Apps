package com.uber.UberApp.dto;

import com.uber.UberApp.entities.Ride;
import com.uber.UberApp.entities.Rider;
import com.uber.UberApp.entities.enums.PaymentMethod;
import com.uber.UberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {


    private Long id;


    private Point pickUpLocation;


    private Point dropLocation;


    private LocalDateTime createdTime;  // Ride Created Time\

    private LocalDateTime startAT;

    private LocalDateTime endedAt;



    private RiderDto rider;

    private DriverDto driverDto;


    private PaymentMethod paymentMethod;


    private RideStatus rideStatus;

    private Double fare;
}
