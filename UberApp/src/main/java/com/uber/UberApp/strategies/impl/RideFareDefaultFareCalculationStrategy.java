package com.uber.UberApp.strategies.impl;

import com.uber.UberApp.dto.RideRequestDto;
import com.uber.UberApp.strategies.RiderFareCalculationStrategy;

public class RideFareDefaultFareCalculationStrategy implements RiderFareCalculationStrategy {
    @Override
    public double CalculateFare(RideRequestDto rideRequestDto) {
        return 0;
    }
}
