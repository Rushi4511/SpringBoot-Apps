package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.dto.DriverDto;
import com.codingshuttle.project.uber.uberApp.dto.RiderDto;
import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.Rating;
import com.codingshuttle.project.uber.uberApp.entities.Ride;
import com.codingshuttle.project.uber.uberApp.entities.Rider;
import com.codingshuttle.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.codingshuttle.project.uber.uberApp.repositories.DriverRepository;
import com.codingshuttle.project.uber.uberApp.repositories.RatingRepository;
import com.codingshuttle.project.uber.uberApp.repositories.RiderRepository;
import com.codingshuttle.project.uber.uberApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {

        Driver driver= ride.getDriver();

        Rating ratingObject= ratingRepository.findByRide(ride).orElseThrow(()->new ResourceNotFoundException("No Rating Found For Driver"));

        if(ratingObject.getDriverRating() != null)
            throw new RuntimeConflictException("Driver is already been Rated");

        ratingObject.setDriverRating(rating);
        ratingRepository.save(ratingObject);

        Double overAllRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average()
                .orElse(0.0);

         driver.setRating(overAllRating);
         Driver savedDriver =driverRepository.save(driver);
         return modelMapper.map(savedDriver, DriverDto.class);


    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {

        Rider rider =ride.getRider();
        Rating ratingObject= ratingRepository.findByRide(ride).orElseThrow(()->new ResourceNotFoundException("No Rating Found For Rider"));


        if(ratingObject.getRiderRating() != null)
            throw new RuntimeConflictException("Rider is already been Rated");

        ratingObject.setRiderRating(rating);
        ratingRepository.save(ratingObject);

        Double overAllRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average()
                .orElse(0.0);

        rider.setRating(overAllRating);
        Rider savedRating = riderRepository.save(rider);
        return modelMapper.map(savedRating, RiderDto.class);

    }

    @Override
    public void createNewRating(Ride ride) {
        Rating newRating = Rating.builder()
                .ride(ride)
                .driver(ride.getDriver())
                .rider(ride.getRider())
                .build();

        ratingRepository.save(newRating);
    }
}
