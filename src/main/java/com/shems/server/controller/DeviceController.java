package com.shems.server.controller;

import com.shems.server.converter.DeviceToDeviceResponseConverter;
import com.shems.server.service.DeviceService;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DeviceController.class);

    @Inject
    private DeviceService deviceService;

    @Inject
    private DeviceToDeviceResponseConverter deviceToDeviceResponseConverter;

    @GetMapping("{customerId}/get")
    ResponseEntity<List<String>> getDevices(@PathVariable Long customerId) {
        LOGGER.info("Fetching all devices for customer with id {}", customerId);
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(deviceToDeviceResponseConverter.convertAll(deviceService.findAllByCustomerId(customerId)));
        return ResponseEntity.ok().body(List.of("hello"));
    }
}
