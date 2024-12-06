package com.uber.UberApp;

import com.uber.UberApp.dto.RideRequestDto;

public interface RiderFareCalculationStrategy {

    double CalculateFare(RideRequestDto rideRequestDto);
}
