package com.shems.server.service;

import com.shems.server.dao.DeviceRepository;
import com.shems.server.domain.Device;
import com.shems.server.dto.request.DeviceRequest;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.time.Instant.now;

@Service
public class DeviceService {

    @Inject
    private DeviceRepository deviceRepository;

    public List<Device> findAllByCustomerId(Long customerId) {
        return deviceRepository.findAllByUserId(customerId);
    }

    public Device register(DeviceRequest device) {
        Device toSave = new Device();
        toSave.setModelNumber(device.getModelNumber());
        toSave.setType(device.getType());
        toSave.setLocation(null);
        toSave.setEnrollmentDate(Date.from(now()));
        return deviceRepository.save(toSave);
    }

    public Collection<Device> findAllUnregistered(Long customer) {
        return deviceRepository.findAllUnregistered(customer);
    }
}
