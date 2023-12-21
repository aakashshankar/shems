package com.shems.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRequest {

    @NotNull(message = "What's the device's type?")
    @NotBlank(message = "Device type can't be empty")
    private String type;

    @NotNull(message = "What's the device's model number?")
    private String modelNumber;

    @NotNull(message = "What's the device's location?")
    private Long locationId;
}
