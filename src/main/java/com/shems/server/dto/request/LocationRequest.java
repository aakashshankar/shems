package com.shems.server.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequest {

    @NotEmpty(message = "You need to specify the square footage of the location!")
    @NotNull
    private Double squareFootage;

    @NotEmpty(message = "How many bedrooms?")
    @NotNull
    private Integer numberOfBedrooms;

    private Integer numberOfOccupants;

    @NotEmpty(message = "Address cannot be empty")
    @NotNull
    private String address;

    private String zipCode;
}
