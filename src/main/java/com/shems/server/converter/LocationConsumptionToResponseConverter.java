package com.shems.server.converter;

import com.shems.server.dto.response.LocationConsumptionResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationConsumptionToResponseConverter {

    public LocationConsumptionResponse convert(String address, Double total) {
        LocationConsumptionResponse response = new LocationConsumptionResponse();
        response.setAddress(address);
        response.setTotal(total);
        return response;
    }

    public List<LocationConsumptionResponse> convertAll(List<Pair<String, Double>> consumptions) {
        return consumptions.stream().map(c -> this.convert(c.getLeft(), c.getRight())).toList();
    }
}
