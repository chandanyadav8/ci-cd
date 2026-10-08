package com.example.geo_tracking_service.controller;

import com.example.geo_tracking_service.dto.LatestLocation;
import com.example.geo_tracking_service.dto.LocationRequest;
import com.example.geo_tracking_service.dto.LocationResponse;
import com.example.geo_tracking_service.entity.Location;
import com.example.geo_tracking_service.service.LocationService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private LocationService locationService;
    public LocationController(LocationService locationService){
        this.locationService=locationService;
    }
    @GetMapping("/getLocation")
    public String getLocation()
    {
        return "this is my current location";
    }
    @PostMapping
    public ResponseEntity<LocationResponse>saveLocation(@Valid @RequestBody LocationRequest locationRequest)
    {
        LocationResponse location=locationService.saveLocation(locationRequest);
        return ResponseEntity.ok(location);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LocationResponse>>getUserLocations(@PathVariable Long userId)
    {
        return ResponseEntity.ok(locationService.getUserLocations(userId));
    }
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<LatestLocation> getLatestLocation(
            @PathVariable Long userId) {

        LatestLocation location =
                locationService.getLatestLocation(userId);

        return ResponseEntity.ok(location);
    }
}
