package com.example.geo_tracking_service.service;

import com.example.geo_tracking_service.dto.LatestLocation;
import com.example.geo_tracking_service.dto.LocationRequest;
import com.example.geo_tracking_service.dto.LocationResponse;
import com.example.geo_tracking_service.entity.Location;
import com.example.geo_tracking_service.exception.ResourceNotFoundException;
import com.example.geo_tracking_service.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final RedisLocationService redisLocationService;
    public LocationService(
            LocationRepository locationRepository,
            RedisLocationService redisLocationService) {

        this.locationRepository = locationRepository;
        this.redisLocationService = redisLocationService;
    }

    public LocationResponse saveLocation(LocationRequest locationRequest)
    {
       Location location=new Location(locationRequest.getUserId(),locationRequest.getLatitude(),locationRequest.getLongitude(), LocalDateTime.now()
       );
        Location locationResponse= locationRepository.save(location);
        redisLocationService.saveLatestLocation(
                new LatestLocation(
                        locationResponse.getUserId(),
                        locationResponse.getLatitude(),
                        locationResponse.getLongitude(),
                        locationResponse.getCapturedAt()
                )
        );

        return toResponse(locationResponse);
    }
    public List<LocationResponse> getUserLocations(Long userId) {
        List<Location> locations =
                locationRepository.findByUserIdOrderByCapturedAtDesc(userId);

        if (locations.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No locations found for user: " + userId
            );
        }

        return locations.stream()
                .map(this::toResponse)
                .toList();  }
    public LocationResponse toResponse(Location location)
    {
        LocationResponse locationResponse=new LocationResponse(location.getId(),location.getUserId(),location.getLatitude(),location.getLongitude(), LocalDateTime.now()
        );
        return  locationResponse;
    }
    public LatestLocation getLatestLocation(Long userId) {

        return redisLocationService.getLatestLocation(userId);
    }
}
