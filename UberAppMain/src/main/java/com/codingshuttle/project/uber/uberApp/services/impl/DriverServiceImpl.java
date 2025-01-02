package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.dto.DriverDto;
import com.codingshuttle.project.uber.uberApp.dto.RideDto;
import com.codingshuttle.project.uber.uberApp.dto.RiderDto;
import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.Ride;
import com.codingshuttle.project.uber.uberApp.entities.RideRequest;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideStatus;
import com.codingshuttle.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.project.uber.uberApp.repositories.DriverRepository;
import com.codingshuttle.project.uber.uberApp.services.DriverService;
import com.codingshuttle.project.uber.uberApp.services.RideRequestService;
import com.codingshuttle.project.uber.uberApp.services.RideService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;

    private final RideService rideService;

    private final ModelMapper modelMapper;
    private final DriverService driverService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {

        RideRequest rideRequest=rideRequestService.findRideRequestById(rideRequestId);


        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("RideRequest Cannot be Accepted, Status is "+rideRequest.getRideRequestStatus());

        }
        Driver currentDriver=getCurrentDriver();
        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Driver Cannot Accept Ride Due to Unavailability");

        }
        currentDriver.setAvailable(false);
        Driver savedDriver = driverRepository.save(currentDriver);

        Ride ride =rideService.createNewRide(rideRequest,savedDriver);





//        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);




        return modelMapper.map(ride,RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {

        Ride ride=rideService.getRideById(rideId);

        Driver driver =getCurrentDriver();

        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver Cannot Start The Ride As He Has not Accepted it Earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride Status Is not ONGOING, Hence Cannot be CANCELLED,STATUS: "+ride.getRideStatus());
        }


        rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(),true);


        return modelMapper.map(ride, RideDto.class);


    }

    @Override
    public RideDto startRide(Long rideId,String otp) {
        Ride ride= rideService.getRideById(rideId);
        Driver driver =getCurrentDriver();

        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver Cannot Start The Ride As He Has not Accepted it Earlier");
        }


        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride Status Is not Confirmed, Hence Cannot be Started,STATUS: "+ride.getRideStatus());
        }

        if(!otp.equals(ride.getOtp())){
            throw new RuntimeException("OTP is Not Valid, OTP "+otp);
        }

        ride.setStartedAt(LocalDateTime.now());

        Ride savedRide =rideService.updateRideStatus(ride,RideStatus.ONGOING);
        return modelMapper.map(savedRide,RideDto.class);

    }

    @Override
    public RideDto endRide(Long rideId) {
        return null;
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver=getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver=getCurrentDriver();

        return rideService.getAllRidesOfDriver(currentDriver,pageRequest).map(
                ride -> modelMapper.map(ride,RideDto.class)

        );
    }

    @Override
    public Driver getCurrentDriver() {
        return  driverRepository.findById(2L).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Driver Not Found for ID:"+2
                )
        );
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        Driver toUpdateDriver =driverRepository.findById(driver.getId()).orElseThrow(()->new RuntimeException("No Driver Available with Id"+driver.getId()));
        toUpdateDriver.setAvailable(available);
        return driverRepository.save(toUpdateDriver);

    }
}
