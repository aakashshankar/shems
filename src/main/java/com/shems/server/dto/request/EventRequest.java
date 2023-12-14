package com.shems.server.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventRequest {

    private Long deviceId;

    private String eventType;

    private Date from;

    private Date to;
}
