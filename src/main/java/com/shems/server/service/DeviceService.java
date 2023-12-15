package com.shems.server.service;

import com.shems.server.dao.DeviceRepository;
import com.shems.server.domain.Device;
import com.shems.server.dto.request.DeviceRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static java.time.Instant.now;

@Service
public class DeviceService {

    @Inject
    private DeviceRepository deviceRepository;

    @Inject
    private LocationService locationService;

    public List<Device> findAllByCustomerId(Long customerId) {
        return deviceRepository.findAllByUserId(customerId);
    }

    public Device register(DeviceRequest device) {
        Device toSave = new Device();
        toSave.setModelNumber(device.getModelNumber());
        toSave.setType(device.getType());
        toSave.setLocation(locationService.findById(device.getLocationId()));
        toSave.setEnrollmentDate(Date.from(now()));
        return deviceRepository.save(toSave);
    }

    public Collection<Device> findAllUnregistered(Long customer) {
        return deviceRepository.findAllUnregistered(customer);
    }

    public void delete(Long deviceId) {
        long before = deviceRepository.count();
        deviceRepository.deleteById(deviceId);
        long after = deviceRepository.count();
        if (before == after) {
            throw new BadRequestException(format("Device with id %d does not exist", deviceId));
        }
    }

    public void delete(Collection<Long> deviceIds) {
        long before = deviceRepository.count();
        deviceRepository.deleteByIds(deviceIds);
        long after = deviceRepository.count();
        if (before == after) {
            throw new BadRequestException(format("Devices with ids %s do not exist", deviceIds));
        }
    }
}
