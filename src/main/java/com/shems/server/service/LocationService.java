package com.shems.server.service;

import com.shems.server.dao.LocationRepository;
import com.shems.server.domain.Location;
import com.shems.server.dto.request.LocationRequest;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

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
}
