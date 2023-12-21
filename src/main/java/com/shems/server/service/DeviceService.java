package com.shems.server.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shems.server.dao.DeviceRepository;
import com.shems.server.domain.Device;
import com.shems.server.dto.request.DeviceRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.time.Instant.EPOCH;
import static java.time.Instant.now;
import static java.util.stream.Collectors.toMap;

@Service
public class DeviceService {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DeviceService.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String ALLOWED_TYPES_FILENAME = "classpath:devicetypes.json";

    @Inject
    private DeviceRepository deviceRepository;

    @Inject
    private LocationService locationService;

    public List<Device> findAllByCustomerId(Long customerId) {
        return deviceRepository.findAllByUserId(customerId);
    }

    public Device register(DeviceRequest device) {
        validateAllowedType(device.getType(), device.getModelNumber());
        Device toSave = new Device();
        toSave.setModelNumber(device.getModelNumber());
        toSave.setType(device.getType());
        toSave.setLocation(locationService.findById(device.getLocationId()));
        toSave.setEnrollmentDate(Date.from(now()));

        LOGGER.info("Saving device {}", toSave);
        return deviceRepository.save(toSave);
    }

    private void validateAllowedType(String type, String modelNumber) {
        try {
            File allowed = ResourceUtils.getFile(ALLOWED_TYPES_FILENAME);
            Map<String, List<String>> allowedTypes = MAPPER.readValue(allowed, new TypeReference<>() {});
            if (!allowedTypes.containsKey(type) || !allowedTypes.get(type).contains(modelNumber)) {
                throw new BadRequestException(format("Device type %s and model number %s is not allowed", type, modelNumber));
            }
        } catch (IOException e) {
            LOGGER.error("Error reading allowed types file", e);
            throw new BadRequestException("Error reading allowed types file");
        }
    }

    public Collection<Device> findAllUnregistered(Long customer) {
        return deviceRepository.findAllUnregistered(customer);
    }

    public void delete(Long deviceId) {
        long before = deviceRepository.count();
        deviceRepository.deleteById(deviceId);
        long after = deviceRepository.count();
        if (before == after) {
            LOGGER.error("Device with id {} does not exist", deviceId);
            throw new BadRequestException(format("Device with id %d does not exist", deviceId));
        }
    }

    public void delete(Collection<Long> deviceIds) {
        long before = deviceRepository.count();
        deviceRepository.deleteByIds(deviceIds);
        long after = deviceRepository.count();
        if (before == after) {
            LOGGER.error("Devices with ids {} do not exist", deviceIds);
            throw new BadRequestException(format("Devices with ids %s do not exist", deviceIds));
        }
    }

    public Collection<Device> findAllByLocationId(Long customerId, Long locationId) {
        return deviceRepository.findAllByLocationId(customerId, locationId);
    }

    public List<Pair<String, Double>> getTopConsumption(Long customerId) {
        List<Pair<String, Double>> top =
                deviceRepository.getTopConsumption(customerId,
                        Date.from(EPOCH), Date.from(now()))
                        .stream().map(c -> Pair.of(c.getType(), c.getTotal())).toList();
        return top.stream().collect(toMap(Pair::getLeft, Pair::getRight, Double::sum)).entrySet().stream()
                .map(e -> Pair.of(e.getKey(), e.getValue())).toList();
    }

}
