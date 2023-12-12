package com.shems.server.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DeviceResponse {

    private String type;

    private Integer modelNumber;

    private LocationResponse location;

    private Date enrollmentDate;
}
