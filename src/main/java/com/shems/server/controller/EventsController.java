package com.shems.server.controller;

import com.shems.server.dto.request.EventRequest;
import com.shems.server.dto.response.EventResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventsController {

    @GetMapping("/get_for_interval")
    ResponseEntity<EventResponse> getEventsForInterval(@RequestBody @Valid EventRequest request) {
        // TODO implement
        return null;
    }
}
