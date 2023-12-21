package com.shems.server.service;

import com.shems.server.dao.LocationRepository;
import com.shems.server.dao.projection.TimeseriesLocationConsumption;
import com.shems.server.domain.Location;
import com.shems.server.dto.request.LocationRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static java.time.Instant.EPOCH;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class LocationService {

    @Inject
    private LocationRepository repository;

    @Inject
    private UserService userService;

    public Location findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(format("Location with id: %s not found", id)));
    }

    public Location register(Long customerId, LocationRequest request) {
        Location toSave = new Location();
        toSave.setAddress(request.getAddress());
        toSave.setUser(userService.findById(customerId));
        toSave.setNumberOfBedrooms(request.getNumberOfBedrooms());
        toSave.setSquareFootage(request.getSquareFootage());
        toSave.setZipCode(request.getZipCode());
        toSave.setNumberOfOccupants(request.getNumberOfOccupants());
        toSave.setStartDate(Date.from(now()));
        repository.save(toSave);
        return toSave;
    }

    public Collection<Location> getAllForUser(Long customerId) {
        return repository.findAllByUserId(customerId);
    }

    public void delete(Long customerId, Collection<Long> locationIds) {
        Collection<Location> locations = repository.findAllById(locationIds);
        if (locations.size() != locationIds.size()) {
            throw new BadRequestException("Some of the locations were not found");
        }
        locations.forEach(location -> {
            if (!location.getUser().getId().equals(customerId)) {
                throw new BadRequestException("Some of the locations do not belong to the current user");
            }
        });
        repository.deleteByIds(locationIds);
    }

    public List<Pair<String, Double>> getConsumption(Long customerId) {
        return repository.findConsumption(customerId, Date.from(EPOCH), Date.from(now()))
                .stream().map(c -> Pair.of(c.getAddress(), c.getTotal())).toList();
    }

    public Collection<TimeseriesLocationConsumption> getConsumptionInterval(Long customerId, Long locationId, String last) {
        Collection<TimeseriesLocationConsumption> consumptions;
        if ("day".equals(last)) {
            consumptions = repository.findHourlyConsumptionForALocation(customerId, locationId,
                    Date.from(now().minus(1, DAYS)), Date.from(now()));
        } else {
            Pair<Date, Date> range = identifyInterval(last);
            Date from = range.getLeft();
            Date to = range.getRight();
            consumptions = repository.findDailyConsumptionForALocation(customerId, locationId, from, to);
        }
        return consumptions;
    }

    private Pair<Date, Date> identifyInterval(String last) {
        Date from, to;
        switch (last) {
            case "three_months" -> {
                from = Date.from(now().minus(90, DAYS));
                to = Date.from(now());
            }
            case "month" -> {
                from = Date.from(now().minus(30, DAYS));
                to = Date.from(now());
            }
            case "week" -> {
                from = Date.from(now().minus(7, DAYS));
                to = Date.from(now());
            }
            default -> throw new BadRequestException("Invalid last (interval) parameter");
        }
        return Pair.of(from, to);
    }

    public Collection<TimeseriesLocationConsumption> getConsumptionInterval(Long customerId, String last) {
        if ("day".equals(last)) {
            return repository.findDailyConsumptionForAllLocations(customerId,
                    Date.from(now().minus(1, DAYS)), Date.from(now()));
        } else {
            Pair<Date, Date> range = identifyInterval(last);
            Date from = range.getLeft();
            Date to = range.getRight();
            return repository.findDailyConsumptionForAllLocations(customerId, from, to);
        }
    }

    public Pair<Double, Double> getTotalConsumption(Long customerId) {
        Double consumption = repository.findTotalConsumption(customerId,
                Date.from(now().minus(30, DAYS)), Date.from(now()));
        Double consumptionLastMonth = repository.findTotalConsumption(customerId,
                Date.from(now().minus(60, DAYS)), Date.from(now().minus(30, DAYS)));
        Double percentageDelta = ((consumptionLastMonth - consumption)
                / consumptionLastMonth) * 100;
        return Pair.of(consumption, percentageDelta);
    }

    public Pair<Double, Double> getAvgConsumption(Long customerId) {
        Double consumption = repository.findAvgConsumption(customerId,
                Date.from(now().minus(30, DAYS)), Date.from(now()));
        Double consumptionLastMonth = repository.findAvgConsumption(customerId,
                Date.from(now().minus(60, DAYS)), Date.from(now().minus(30, DAYS)));
        Double percentageDelta = ((consumptionLastMonth - consumption)
                / consumptionLastMonth) * 100;
        return Pair.of(consumption, percentageDelta);
    }
}
