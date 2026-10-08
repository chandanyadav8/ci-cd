package com.example.geo_tracking_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor

public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Double latitude;
    @Column (nullable = false)
    private Double longitude;
    @Column(nullable = false)
    private LocalDateTime capturedAt;
    public Location(Long userId,Double latitude,Double longitude,LocalDateTime capturedAt)
    {
        this.userId=userId;
        this.latitude=latitude;
        this.longitude=longitude;
        this.capturedAt=capturedAt;
    }


}
