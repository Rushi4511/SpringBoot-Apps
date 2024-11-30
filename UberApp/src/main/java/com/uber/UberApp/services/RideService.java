package com.uber.UberApp.services;

import com.uber.UberApp.dto.RideDto;
import com.uber.UberApp.dto.RideRequestDto;
import com.uber.UberApp.entities.Driver;
import com.uber.UberApp.entities.Ride;
import com.uber.UberApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface RideService {

    Ride getRideById(Long rideId);

    void matchWithDriver(RideRequestDto rideRequestDto);

    Ride createNewRide(RideRequestDto rideRequestDto, Driver driver);

    Ride updateRideStatus(Long rideId, RideStatus rideStatus);

    Page<RideDto> getAllRidesOfRider(Long riderId, PageRequest pageRequest);

    Page<RideDto> getAllRidesOfDriver(Long driverId, PageRequest pageRequest);


}
