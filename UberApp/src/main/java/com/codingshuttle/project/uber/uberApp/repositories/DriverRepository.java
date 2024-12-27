package com.codingshuttle.project.uber.uberApp.repositories;

import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.RideRequest;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


// ST_DISTANCE(point1 ,point2)
// ST_DWITHIN()

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("SELECT d.* ,ST_DISTANCE(d.current_location,:pickupLocation) AS distance "+
            "FROM drivers AS d"+
            " where = true AND ST_DWithin(d.current_location,:pickupLocation,1000)"+
            "ORDER BY distance"+
            "LIMIT 10"
    )
    List<Driver> findMatchingDriver(Point pickupLocation);
}
