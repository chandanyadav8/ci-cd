package com.example.geo_tracking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {

    private Long id;
    private Long userId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime capturedAt;
}
