package com.shems.server.service;

import com.shems.server.dao.DeviceRepository;
import com.shems.server.domain.Device;
import com.shems.server.dto.request.DeviceRequest;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return deviceRepository.save(toSave);
    }
}
