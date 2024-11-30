package com.uber.UberApp.services.impl;

import com.uber.UberApp.services.DistanceService;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public class DistanceServiceImpl implements DistanceService {
    @Override
    public double calculateDistance(Point src, Point destin) {
        return 0;
    }
}
