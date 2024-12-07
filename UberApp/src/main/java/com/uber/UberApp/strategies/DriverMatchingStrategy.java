package com.uber.UberApp.strategies;

import com.uber.UberApp.dto.RideRequestDto;
import com.uber.UberApp.entities.Driver;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequestDto rideRequestDto);
}
