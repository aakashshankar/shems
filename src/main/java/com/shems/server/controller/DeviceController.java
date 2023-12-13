package com.shems.server.controller;

import com.shems.server.converter.DeviceToDeviceResponseConverter;
import com.shems.server.dto.request.DeviceRequest;
import com.shems.server.dto.response.DeviceResponse;
import com.shems.server.service.DeviceService;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<List<DeviceResponse>> getDevices(@PathVariable Long customerId) {
        LOGGER.info("Fetching all devices for customer with id {}", customerId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deviceToDeviceResponseConverter.convertAll(deviceService.findAllByCustomerId(customerId)));
    }

    @PostMapping("{customerId}/add")
    ResponseEntity<DeviceResponse> addDevice(@PathVariable Long customerId, @RequestBody DeviceRequest device) {
        LOGGER.info("Adding device {} for customer with id {}", device, customerId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deviceToDeviceResponseConverter.convert(deviceService.register(customerId, device)));
    }


}
