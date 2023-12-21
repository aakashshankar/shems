package com.shems.server.controller;

import com.shems.server.context.UserContext;
import com.shems.server.converter.LocationConsumptionToResponseConverter;
import com.shems.server.converter.LocationToLocationResponseConverter;
import com.shems.server.dto.request.LocationRequest;
import com.shems.server.dto.response.LocationConsumptionResponse;
import com.shems.server.dto.response.LocationResponse;
import com.shems.server.dto.response.LocationTimeseriesResponse;
import com.shems.server.service.LocationService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(LocationController.class);

    @Inject
    private LocationService locationService;

    @Inject
    private LocationToLocationResponseConverter converter;

    @Inject
    private LocationConsumptionToResponseConverter consumptionConverter;

    @PostMapping("register")
    ResponseEntity<LocationResponse> register(@RequestBody @Valid LocationRequest request) {
        Long customerId = UserContext.getCurrentUser();
        LOGGER.info("Registering location with zipcode: {}, for customer: {}", request.getZipCode(), customerId);
        return ResponseEntity.ok().body(converter.convert(locationService.register(customerId, request)));
    }

    @GetMapping("all")
    ResponseEntity<List<LocationResponse>> getAll() {
        Long customerId = UserContext.getCurrentUser();
        LOGGER.info("Fetching all locations for current user: {}", customerId);
        return ResponseEntity.ok().body(converter.convertAll(locationService.getAllForUser(customerId)));
    }

    @DeleteMapping("delete_multiple")
    ResponseEntity<Void> deleteMultiple(@RequestBody List<Long> locationIds) {
        Long customerId = UserContext.getCurrentUser();
        LOGGER.info("Deleting locations: {} for customer: {}", locationIds, customerId);
        locationService.delete(customerId, locationIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("consumption")
    ResponseEntity<List<LocationConsumptionResponse>> consumption() {
        Long customerId = UserContext.getCurrentUser();
        LOGGER.info("Fetching top consuming locations for customer: {}", customerId);
        return ResponseEntity.ok().body(consumptionConverter.convertAll(locationService.getConsumption(customerId)));
    }

    @GetMapping("total")
    ResponseEntity<LocationConsumptionResponse> total() {
        Long customerId = UserContext.getCurrentUser();
        LOGGER.info("Fetching total consumption for customer: {}", customerId);
        return ResponseEntity.ok().body(consumptionConverter.convertTotalAndAvg(locationService.getTotalConsumption(customerId)));
    }

    @GetMapping("avg")
    ResponseEntity<LocationConsumptionResponse> avg() {
        Long customerId = UserContext.getCurrentUser();
        LOGGER.info("Fetching average consumption for customer: {}", customerId);
        return ResponseEntity.ok().body(consumptionConverter.convertTotalAndAvg(locationService.getAvgConsumption(customerId)));
    }

    @GetMapping("consumption_interval")
    ResponseEntity<List<LocationTimeseriesResponse>> consumptionInInterval(@RequestParam("interval") String interval) {
        Long customerId = UserContext.getCurrentUser();
        LOGGER.info("Fetching all consuming locations for customer: {} within the last {}", customerId, interval);
        return ResponseEntity.ok().body(consumptionConverter.convertAllTimeseries(locationService.getConsumptionInterval(customerId, interval)));
    }

    @GetMapping("{id}/consumption_interval")
    ResponseEntity<List<LocationTimeseriesResponse>> consumptionInInterval(@PathVariable("id") Long locationId,
                                                                            @RequestParam("interval") String interval) {
        Long customerId = UserContext.getCurrentUser();
        LOGGER.info("Fetching all consuming locations for customer: {} within the last {}", customerId, interval);
        return ResponseEntity.ok().body(consumptionConverter.convertAllTimeseries(locationService.getConsumptionInterval(customerId, locationId, interval)));
    }

}
