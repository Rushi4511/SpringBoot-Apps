package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_BASE_API_URL ="http://router.project-osrm.org/route/v1/driving/";


    @Override
    public double calculateDistance(Point source, Point destination) {

        String uri = source.getX()+","+source.getY()+";"+destination.getX()+","+destination.getY();
        System.out.println(uri);
        System.out.println(OSRM_BASE_API_URL);


        try{
            OSRMResponseDto responseDto =RestClient.builder()
                    .baseUrl(OSRM_BASE_API_URL)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(OSRMResponseDto.class);



            //Call the third party API to fetch the distance
            return responseDto.getRoutes().get(0).getDistance()/ 1000.0;

        } catch (Exception e) {
            throw new RuntimeException("Error Getting Message In OSRM"+e.getMessage());
        }



    }


}
@Data
class OSRMResponseDto{
    private List<OSRMRoute> routes;

}

@Data
class  OSRMRoute{
    private Double distance;

}
