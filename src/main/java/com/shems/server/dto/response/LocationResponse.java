package com.shems.server.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse {

    private Long id;

    private String address;

    private String zipCode;

    private Integer numberOfBedrooms;

    private Double squareFootage;

    private Integer numberOfOccupants;
}
