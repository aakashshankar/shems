package com.shems.server.dto.response;

import com.shems.server.dto.EventDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventResponse {

    private Long id;

    private Long deviceId;

    private String eventType;

    private List<EventDto> events;
}
