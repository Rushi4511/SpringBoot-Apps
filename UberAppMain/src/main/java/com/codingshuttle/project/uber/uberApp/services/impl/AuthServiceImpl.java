package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.dto.DriverDto;
import com.codingshuttle.project.uber.uberApp.dto.SignupDto;
import com.codingshuttle.project.uber.uberApp.dto.UserDto;
import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.Rider;
import com.codingshuttle.project.uber.uberApp.entities.User;
import com.codingshuttle.project.uber.uberApp.entities.enums.Role;
import com.codingshuttle.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.codingshuttle.project.uber.uberApp.repositories.UserRepository;
import com.codingshuttle.project.uber.uberApp.services.AuthService;
import com.codingshuttle.project.uber.uberApp.services.DriverService;
import com.codingshuttle.project.uber.uberApp.services.RiderService;
import com.codingshuttle.project.uber.uberApp.services.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.codingshuttle.project.uber.uberApp.entities.enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private  final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    @Transactional
    public UserDto signUp(SignupDto signupDto) {

        User user =userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user!=null){
            throw new RuntimeConflictException("Cannot SignUp, User already exists with email "+signupDto.getEmail());
        }


        User mappedUser = modelMapper.map(signupDto,User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(mappedUser);


        //Create User Related Entities

        Rider rider = riderService.createNewRider(savedUser);

        //   TODO add Wallet related Service
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId,String vehicleId) {
        User user =userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found With ID:"+userId));

        if(user.getRoles().contains(DRIVER))
            throw new RuntimeConflictException("");

        Driver createDriver =Driver.builder()
                .id(userId)
                .rating(4.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();

        user.getRoles().add(DRIVER);
        userRepository.save(user);

        Driver saveDriver =driverService.createNewDriver(createDriver);

        return modelMapper.map(saveDriver, DriverDto.class);


    }
}
