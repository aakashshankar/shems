package com.shems.server.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRequest {

    @NotNull(message = "What's the device's type?")
    private String type;

    @NotNull(message = "What's the device's model number?")
    private Integer modelNumber;

    @NotNull(message = "What's the device's location?")
    private Long locationId;
}
