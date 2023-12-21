package com.shems.server.converter;

import com.shems.server.dto.response.PriceConsumptionResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component
public class PriceToPriceConsumptionResponseConverter {

    public PriceConsumptionResponse convert(Pair<Double, Double> source) {
        PriceConsumptionResponse response = new PriceConsumptionResponse();
        response.setPrice(source.getLeft());
        response.setPercentageDelta(source.getRight());
        return response;
    }

    public PriceConsumptionResponse convert(Double source) {
        PriceConsumptionResponse response = new PriceConsumptionResponse();
        response.setPrice(source);
        return response;
    }
}
