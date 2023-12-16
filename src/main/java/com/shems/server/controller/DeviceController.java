package com.shems.server.controller;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shems.server.context.UserContext;
import com.shems.server.converter.DeviceToDeviceResponseConverter;
import com.shems.server.dto.request.DeleteDevicesRequest;
import com.shems.server.dto.request.DeviceRequest;
import com.shems.server.dto.response.DeviceResponse;
import com.shems.server.service.DeviceService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DeviceController.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

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

    @DeleteMapping("{deviceId}/delete")
    ResponseEntity<Void> deleteDevice(@PathVariable Long deviceId) {
        Long customer = UserContext.getCurrentUser();
        LOGGER.info("Deleting device with id {} for customer with id {}", deviceId, customer);
        deviceService.delete(deviceId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete_multiple")
    ResponseEntity<Void> deleteMultipleDevices(@RequestBody @Valid DeleteDevicesRequest request) {
        Long customer = UserContext.getCurrentUser();
        LOGGER.info("Deleting {} devices for customer with id {}", request.getDeviceIds().size(),
                customer);
        deviceService.delete(request.getDeviceIds());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allowed")
    ResponseEntity<Map<String, List<String>>> getAllowedDevices() {
        try {
            File file = ResourceUtils.getFile("classpath:devicetypes.json");
            Map<String, List<String>> allowedDevices = MAPPER.readValue(file, new TypeReference<>() {
            });
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(allowedDevices);
        } catch (IOException e) {
            throw new BadRequestException("Error reading allowed devices", e);
        }

    }

}
