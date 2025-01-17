package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.dto.DriverDto;
import com.codingshuttle.project.uber.uberApp.dto.RideDto;
import com.codingshuttle.project.uber.uberApp.dto.RiderDto;
import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.Ride;
import com.codingshuttle.project.uber.uberApp.entities.RideRequest;
import com.codingshuttle.project.uber.uberApp.entities.User;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideStatus;
import com.codingshuttle.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.project.uber.uberApp.repositories.DriverRepository;
import com.codingshuttle.project.uber.uberApp.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;


    private final RideService rideService;

    private final ModelMapper modelMapper;

    private final PaymentService paymentService;
    private final RatingService ratingService;

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
//        Driver savedDriver = driverRepository.save(currentDriver);
        Driver savedDriver =updateDriverAvailability(currentDriver,true);

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
        updateDriverAvailability(driver, true);
        driverRepository.save(driver);



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

        paymentService.createNewPayment(savedRide);

        return modelMapper.map(savedRide,RideDto.class);

    }

    @Override
    public RideDto endRide(Long rideId) {
        Ride ride= rideService.getRideById(rideId);
        Driver driver =getCurrentDriver();

        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver Cannot Start The Ride As He Has not Accepted it Earlier");
        }


        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride Status Is not Confirmed, Hence Cannot be Started,STATUS: "+ride.getRideStatus());
        }

        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide=rideService.updateRideStatus(ride,RideStatus.ENDED);
        updateDriverAvailability(driver,true);

        paymentService.processPayment(ride);

        return modelMapper.map(savedRide,RideDto.class);

    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride=rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver is not the Owner Of the Ride");
        }


        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride Status Is not Ended , Hence Cannot be Started,STATUS: "+ride.getRideStatus());
        }


        return ratingService.rateRider(ride,rating);

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

        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return  driverRepository.findByUser(user).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Driver Not Associated with user with ID:"+user.getId()
                )
        );
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        Driver toUpdateDriver =driverRepository.findById(driver.getId()).orElseThrow(()->new RuntimeException("No Driver Available with Id"+driver.getId()));
        toUpdateDriver.setAvailable(available);
        return driverRepository.save(toUpdateDriver);

    }

    @Override
    public Driver createNewDriver(Driver createDriver) {
        return driverRepository.save(createDriver);
    }
}
