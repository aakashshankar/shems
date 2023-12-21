package com.shems.server.converter;

import com.shems.server.domain.Location;
import com.shems.server.dto.response.LocationResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class LocationToLocationResponseConverter {

    public LocationResponse convert(Location src) {
        LocationResponse response = new LocationResponse();
        response.setId(src.getId());
        response.setAddress(src.getAddress());
        response.setZipCode(src.getZipCode());
        response.setNumberOfBedrooms(src.getNumberOfBedrooms());
        response.setSquareFootage(src.getSquareFootage());
        response.setNumberOfOccupants(src.getNumberOfOccupants());

        return response;
    }

    public List<LocationResponse> convertAll(Collection<Location> locations) {
        return locations.stream().map(this::convert).toList();
    }
}
