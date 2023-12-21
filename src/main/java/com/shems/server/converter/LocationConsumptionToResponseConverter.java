package com.shems.server.converter;

import com.shems.server.dao.projection.TimeseriesLocationConsumption;
import com.shems.server.dto.response.LocationConsumptionResponse;
import com.shems.server.dto.response.LocationTimeseriesResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
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

    public LocationConsumptionResponse convertTotalAndAvg(Pair<Double, Double> consumption) {
        LocationConsumptionResponse response = new LocationConsumptionResponse();
        response.setTotal(consumption.getLeft());
        response.setPercentageDelta(consumption.getRight());
        return response;
    }

    public List<LocationConsumptionResponse> convertAll(List<Pair<String, Double>> consumptions) {
        return consumptions.stream().map(c -> this.convert(c.getLeft(), c.getRight())).toList();
    }

    public LocationTimeseriesResponse convert(TimeseriesLocationConsumption consumption) {
        LocationTimeseriesResponse response = new LocationTimeseriesResponse();
        response.setValue(consumption.getTotal());
        response.setTimeUnit(consumption.getTimeUnit());
        return response;
    }

    public List<LocationTimeseriesResponse> convertAllTimeseries(List<TimeseriesLocationConsumption> consumptions) {
        return consumptions.stream().map(this::convert).toList();
    }
}
