package com.shems.server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRequest {

    private String type;

    private Integer modelNumber;

    private Long locationId;
}
