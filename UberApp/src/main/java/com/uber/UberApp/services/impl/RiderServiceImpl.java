package com.uber.UberApp.services.impl;

import com.uber.UberApp.dto.DriverDto;
import com.uber.UberApp.dto.RideDto;
import com.uber.UberApp.dto.RideRequestDto;
import com.uber.UberApp.dto.RiderDto;
import com.uber.UberApp.services.RiderService;
import org.springframework.stereotype.Service;

@Service
public class RiderServiceImpl implements RiderService {
    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        return null;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateRide(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }
}
