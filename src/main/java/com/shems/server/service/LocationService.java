package com.shems.server.service;

import com.shems.server.dao.LocationRepository;
import com.shems.server.dao.projection.LocationAndTotalConsumption;
import com.shems.server.domain.Location;
import com.shems.server.dto.request.LocationRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;

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
        toSave.setStartDate(Date.from(Instant.now()));
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

    public List<Pair<String, Double>> getTopConsumption(Long customerId) {
        return repository.findTopConsumption(customerId).stream().map(c -> Pair.of(c.getAddress(), c.getTotal())).toList();
    }

    public Pair<String, Double> getMostConsuming(Long customerId) {
        LocationAndTotalConsumption consumption = repository.findMostConsuming(customerId);
        return Pair.of(consumption.getAddress(), consumption.getTotal());
    }
}
