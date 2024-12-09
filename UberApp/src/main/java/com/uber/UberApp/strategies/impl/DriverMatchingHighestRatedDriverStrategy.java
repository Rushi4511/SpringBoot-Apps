package com.uber.UberApp.strategies.impl;

import com.uber.UberApp.dto.RideRequestDto;
import com.uber.UberApp.entities.Driver;
import com.uber.UberApp.strategies.DriverMatchingStrategy;

import java.util.List;

public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {
    @Override
    public List<Driver> findMatchingDriver(RideRequestDto rideRequestDto) {
        return List.of();
    }
}
