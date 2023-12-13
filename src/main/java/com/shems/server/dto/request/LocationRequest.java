package com.shems.server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequest {

    private Double squareFootage;

    private Integer numberOfBedrooms;

    private Integer numberOfOccupants;

    private String address;

    private String zipCode;
}
