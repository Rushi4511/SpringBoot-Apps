package com.uber.UberApp.services.impl;

import com.uber.UberApp.dto.RideDto;
import com.uber.UberApp.dto.RideRequestDto;
import com.uber.UberApp.entities.Driver;
import com.uber.UberApp.entities.Ride;
import com.uber.UberApp.entities.enums.RideStatus;
import com.uber.UberApp.services.RideService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RideServiceImpl implements RideService {
    @Override
    public Ride getRideById(Long rideId) {
        return null;
    }

    @Override
    public void matchWithDriver(RideRequestDto rideRequestDto) {

    }

    @Override
    public Ride createNewRide(RideRequestDto rideRequestDto, Driver driver) {
        return null;
    }

    @Override
    public Ride updateRideStatus(Long rideId, RideStatus rideStatus) {
        return null;
    }

    @Override
    public Page<RideDto> getAllRidesOfRider(Long riderId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<RideDto> getAllRidesOfDriver(Long driverId, PageRequest pageRequest) {
        return null;
    }
}
