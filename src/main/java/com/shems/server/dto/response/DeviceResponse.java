package com.shems.server.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DeviceResponse {

    private Long id;

    private String type;

    private String modelNumber;

    private LocationResponse location;

    private Date enrollmentDate;
}
