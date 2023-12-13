package com.shems.server.service;

import com.shems.server.dao.LocationRepository;
import com.shems.server.domain.Location;
import com.shems.server.dto.request.LocationRequest;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class LocationService {

    @Inject
    private LocationRepository repository;

    @Inject
    private UserService userService;

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
}
