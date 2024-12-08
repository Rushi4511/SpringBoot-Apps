package com.uber.UberApp.strategies.impl;

import com.uber.UberApp.dto.RideRequestDto;
import com.uber.UberApp.entities.Driver;
import com.uber.UberApp.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {


    @Override
    public List<Driver> findMatchingDriver(RideRequestDto rideRequestDto) {
        return List.of();
    }
}
