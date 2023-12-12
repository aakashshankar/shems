package com.shems.server.converter;

import com.shems.server.domain.Device;
import com.shems.server.dto.response.DeviceResponse;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class DeviceToDeviceResponseConverter {

    @Inject
    private LocationToLocationResponseConverter locationToLocationResponseConverter;

    public DeviceResponse convert(Device source) {
        DeviceResponse response = new DeviceResponse();
        response.setType(source.getType());
        response.setModelNumber(source.getModelNumber());
        response.setLocation(locationToLocationResponseConverter.convert(source.getLocation()));
        return response;
    }

    public List<DeviceResponse> convertAll(Collection<Device> devices) {
        return devices.stream().map(this::convert).toList();
    }
}
