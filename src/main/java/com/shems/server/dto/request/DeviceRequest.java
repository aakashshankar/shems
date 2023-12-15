package com.shems.server.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRequest {

    @NotEmpty(message = "Type cannot be empty")
    @NotNull
    private String type;

    @NotEmpty(message = "Model number cannot be empty")
    @NotNull
    private Integer modelNumber;

    @NotEmpty(message = "Device needs to have a location!")
    @NotNull
    private Long locationId;
}
