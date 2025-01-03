package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.dto.DriverDto;
import com.codingshuttle.project.uber.uberApp.dto.RideDto;
import com.codingshuttle.project.uber.uberApp.dto.RideRequestDto;
import com.codingshuttle.project.uber.uberApp.dto.RiderDto;
import com.codingshuttle.project.uber.uberApp.entities.*;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideStatus;
import com.codingshuttle.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.project.uber.uberApp.repositories.RideRequestRepository;
import com.codingshuttle.project.uber.uberApp.repositories.RiderRepository;
import com.codingshuttle.project.uber.uberApp.services.DriverService;
import com.codingshuttle.project.uber.uberApp.services.RatingService;
import com.codingshuttle.project.uber.uberApp.services.RideService;
import com.codingshuttle.project.uber.uberApp.services.RiderService;
import com.codingshuttle.project.uber.uberApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;

    private final RideRequestRepository rideRequestRepository;

    private  final RiderRepository riderRepository;

    private final RideStrategyManager rideStrategyManager;

    private final RideService rideService;

    private final DriverService driverService;

    private final RatingService ratingService;


    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        Rider rider = getCurrentRider();

        RideRequest rideRequest =modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        double fare= rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);




        RideRequest saveRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers =rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);



        // TODO :Send Notification to all the drivers about this ride


        return modelMapper.map(saveRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);

        Rider rider =getCurrentRider();
        if (!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider Cannot Start The Ride As He Has not Accepted it Earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride Status Is not ONGOING, Hence Cannot be CANCELLED,STATUS: "+ride.getRideStatus());
        }

        Ride savedRide=rideService.updateRideStatus(ride,RideStatus.CANCELLED);

        driverService.updateDriverAvailability(ride.getDriver(),true);



        return modelMapper.map(savedRide,RideDto.class);

    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {

        Ride ride=rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if (!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider is not the Owner Of the Ride");
        }


        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride Status Is not Ended , Hence Cannot be Started,STATUS: "+ride.getRideStatus());
        }


        return ratingService.rateDriver(ride,rating);


    }

    @Override
    public RiderDto getMyProfile() {
        Rider rider = getCurrentRider();
        return modelMapper.map(rider,RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider=getCurrentRider();

        return rideService.getAllRidesOfRider(currentRider,pageRequest).map(
                ride -> modelMapper.map(ride,RideDto.class)

        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();

        return riderRepository.save(new Rider());

    }

    @Override
    public Rider getCurrentRider() {

        // TODO :implement Spring Security
        return riderRepository.findById(1L).orElseThrow(()-> new ResourceNotFoundException(
                "Rider Not Found With Id"+1
        ));
    }
}
