package com.shems.server.service;

import com.shems.server.dao.PriceRepository;
import jakarta.inject.Inject;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Objects.nonNull;

@Service
public class PriceService {

    @Inject
    private PriceRepository repository;

    public Pair<Double, Double> getTotal(Long customerId) {
        Double total = repository.getTotal(customerId, Date.from(now().minus(30, DAYS)), Date.from(now()));
        Double totalLastMonth = repository.getTotal(customerId, Date.from(now().minus(60, DAYS)), Date.from(now().minus(30, DAYS)));
        if (nonNull(total)) {
            Double percentageDelta = ((total - totalLastMonth) / totalLastMonth) * 100;
            return Pair.of(total, percentageDelta);
        }
        return Pair.of(0.0, 0.0);
    }


    public Double getHistory(Long userId) {
        return repository.getHistory(userId);
    }
}
