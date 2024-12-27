package com.uber.UberApp.services.impl;

import com.uber.UberApp.dto.DriverDto;
import com.uber.UberApp.dto.RideDto;
import com.uber.UberApp.dto.RideRequestDto;
import com.uber.UberApp.dto.RiderDto;
import com.uber.UberApp.entities.RideRequest;
import com.uber.UberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j//for logs
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        RideRequest rideRequest= modelMapper.map(rideRequestDto,RideRequest.class);

        log.info(rideRequest.toString());

        return null;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateRide(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RiderDto> getAllMyRides() {
        return List.of();
    }
}
