package com.shems.server.converter;

import com.shems.server.dto.response.DeviceConsumptionResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class DeviceConsumptionResponseConverter {

    public DeviceConsumptionResponse convert(String type, Double value) {
        DeviceConsumptionResponse response = new DeviceConsumptionResponse();
        response.setType(type);
        response.setValue(value);
        return response;
    }

    public DeviceConsumptionResponse convert(Triple<String, Double, Double> consumption) {
        DeviceConsumptionResponse response = this.convert(consumption.getLeft(), consumption.getMiddle());
        response.setPercentageDelta(consumption.getRight());
        return response;
    }

    public List<DeviceConsumptionResponse> convertAll(Collection<Pair<String, Double>> values) {
        return values.stream().map(c -> this.convert(c.getLeft(), c.getRight())).toList();
    }
}
