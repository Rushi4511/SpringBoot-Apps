package com.uber.UberApp.strategies;

import com.uber.UberApp.dto.RideRequestDto;

public interface RiderFareCalculationStrategy {

    double CalculateFare(RideRequestDto rideRequestDto);
}
