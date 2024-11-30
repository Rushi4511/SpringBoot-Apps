package com.uber.UberApp.dto;

import com.uber.UberApp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private UserDto user;

    private Double rating;
}
