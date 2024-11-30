package com.uber.UberApp.services;

import com.uber.UberApp.dto.DriverDto;
import com.uber.UberApp.dto.UserDto;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String login(String email,String password);

    UserDto onBoardDriver(Long userId);


}
