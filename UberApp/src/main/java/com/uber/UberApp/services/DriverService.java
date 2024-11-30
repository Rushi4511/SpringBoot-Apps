package com.uber.UberApp.services;


import com.uber.UberApp.dto.DriverDto;
import com.uber.UberApp.dto.RideDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DriverService {

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId);

    RideDto endRide(Long rideId);

    RideDto rateRide(Long rideId,Integer rating);

    RideDto acceptRide(Long rideId);

    DriverDto getMyProfile();

    List<RideDto> getAllMyRides;


}
