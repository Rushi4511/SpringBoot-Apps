package com.uber.UberApp.services;

import com.uber.UberApp.dto.DriverDto;
import com.uber.UberApp.dto.RideDto;
import com.uber.UberApp.dto.RideRequestDto;
import com.uber.UberApp.dto.RiderDto;
import com.uber.UberApp.entities.RideRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateRide(Long rideId,Integer rating);

    RiderDto getMyProfile();

    List<RiderDto> getAllMyRides();
}
