package com.uber.UberApp.services.impl;

import com.uber.UberApp.dto.UserDto;
import com.uber.UberApp.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public UserDto onBoardDriver(Long userId) {
        return null;
    }
}
