package com.uber.UberApp.dto;

import com.uber.UberApp.entities.Rider;
import com.uber.UberApp.entities.enums.PaymentMethod;
import com.uber.UberApp.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {


    private Long id;


    private Point pickUpLocation;


    private Point dropOfLocation;


    private LocalDateTime requestTime;


    private RiderDto riderDto;


    private PaymentMethod paymentMethod;


    private RideRequestStatus rideRequestStatus;
}