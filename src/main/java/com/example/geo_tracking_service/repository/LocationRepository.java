package com.example.geo_tracking_service.repository;

import com.example.geo_tracking_service.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    List<Location> findByUserIdOrderByCapturedAtDesc(Long userId);

}
