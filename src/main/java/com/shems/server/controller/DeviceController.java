package com.shems.server.controller;

import com.shems.server.context.UserContext;
import com.shems.server.converter.DeviceToDeviceResponseConverter;
import com.shems.server.dto.request.DeviceRequest;
import com.shems.server.dto.response.DeviceResponse;
import com.shems.server.service.DeviceService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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

    @GetMapping("/get")
    ResponseEntity<List<DeviceResponse>> getDevices() {
        Long customerId = UserContext.getCurrentUser();
        LOGGER.info("Fetching all devices for customer with id {}", customerId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deviceToDeviceResponseConverter.convertAll(deviceService.findAllByCustomerId(customerId)));
    }

    @PostMapping("/add")
    ResponseEntity<DeviceResponse> addDevice(@RequestBody @Valid  DeviceRequest device) {
        Long customerId = UserContext.getCurrentUser();
        LOGGER.info("Adding device {} for customer with id {}", device, customerId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deviceToDeviceResponseConverter.convert(deviceService.register(device)));
    }

    @GetMapping("/unregistered")
    ResponseEntity<List<DeviceResponse>> getUnregisteredDevices() {
        Long customer = UserContext.getCurrentUser();
        LOGGER.info("Fetching all unregistered devices for customer with id {}", customer);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deviceToDeviceResponseConverter.convertAll(deviceService.findAllUnregistered(customer)));
    }

}
