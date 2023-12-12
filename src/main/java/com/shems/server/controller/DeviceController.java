package com.shems.server.controller;

import com.shems.server.converter.DeviceToDeviceResponseConverter;
import com.shems.server.dto.response.DeviceResponse;
import com.shems.server.service.DeviceService;
import jakarta.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("api/v1/device")
public class DeviceController {

    @Inject
    private DeviceService deviceService;

    @Inject
    private DeviceToDeviceResponseConverter deviceToDeviceResponseConverter;

    @GetMapping("{customerId}/get")
    ResponseEntity<List<DeviceResponse>> getDevices(@PathVariable Long customerId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deviceToDeviceResponseConverter.convertAll(deviceService.findAllByCustomerId(customerId)));
    }
}
