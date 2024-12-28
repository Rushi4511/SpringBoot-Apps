package com.codingshuttle.project.uber.uberApp.strategies;

import com.codingshuttle.project.uber.uberApp.entities.RideRequest;


public interface RideFareCalculationStrategy {


    //Give 10 Rs per 1km

    double RIDE_FARE_MULTIPLIER =10;

    double calculateFare(RideRequest rideRequest);

}
