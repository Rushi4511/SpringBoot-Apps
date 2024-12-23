package com.uber.UberApp.services;

import com.uber.UberApp.dto.UserDto;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public interface DistanceService {

    double calculateDistance(Point src, Point destin);
}
