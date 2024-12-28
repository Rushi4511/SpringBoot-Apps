package com.codingshuttle.project.uber.uberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {

    private double[] coordinate;

    private String type="Point";

    public PointDto(double[] coordinate) {
        this.coordinate = coordinate;
    }
}
