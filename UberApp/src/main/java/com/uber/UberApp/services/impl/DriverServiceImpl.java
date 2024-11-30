package com.uber.UberApp.services.impl;

import com.uber.UberApp.dto.DriverDto;
import com.uber.UberApp.dto.RideDto;
import com.uber.UberApp.services.DriverService;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {
    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto startRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto endRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto rateRide(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RideDto acceptRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        return null;
    }
}
