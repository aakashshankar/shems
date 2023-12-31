package com.shems.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequest {

    @NotNull(message = "You need to specify the square footage!")
    private Double squareFootage;

    @NotNull(message = "You need to specify the number of bedrooms!")
    private Integer numberOfBedrooms;

    @NotNull(message = "How many people live in this location?")
    private Integer numberOfOccupants;

    @NotNull(message = "You need to specify the address!")
    @NotBlank(message = "Why is the address empty?")
    private String address;

    private String zipCode;
}
