package com.codingshuttle.project.uber.uberApp.repositories;

import com.codingshuttle.project.uber.uberApp.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


// ST_DISTANCE(point1 ,point2)
// ST_DWITHIN()

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    //This Query is Optimized itself because it uses indexing

    @Query(value = "SELECT d.*,ST_DISTANCE(d.current_location,:pickupLocation) AS distance "+
            " FROM driver d"+
            " where d.available = true AND ST_DWithin(d.current_location,:pickupLocation,1000)"+
            " ORDER BY distance"+
            " LIMIT 10",nativeQuery = true
    )
    List<Driver> findTenNearestDriver(Point pickupLocation);

    @Query(value =  "SELECT d.* "+
                    "FROM driver d "+
                    "where d.available = true AND ST_DWithin(d.current_location,:pickupLocation,15000) "+
                    "ORDER BY d.rating DESC "+
                    "LIMIT 10",nativeQuery = true
    )
    List<Driver> findNearByTopRatedDriver(Point pickupLocation);
}
