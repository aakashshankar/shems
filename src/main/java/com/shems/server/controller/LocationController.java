package com.shems.server.controller;

import com.shems.server.converter.LocationToLocationResponseConverter;
import com.shems.server.dto.request.LocationRequest;
import com.shems.server.dto.response.LocationResponse;
import com.shems.server.service.LocationService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    @Inject
    private LocationService locationService;

    @Inject
    private LocationToLocationResponseConverter converter;

    @PostMapping("{customerId}/register")
    ResponseEntity<LocationResponse> register(@PathVariable Long customerId,
                                              @RequestBody @Valid LocationRequest request) {
        return ResponseEntity.ok().body(converter.convert(locationService.register(customerId, request)));
    }
}
