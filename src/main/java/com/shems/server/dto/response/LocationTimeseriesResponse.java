package com.shems.server.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationTimeseriesResponse {

    private String address;

    private Double total;

    private String timeUnit;
}
