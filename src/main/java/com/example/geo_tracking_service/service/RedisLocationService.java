package com.example.geo_tracking_service.service;

import com.example.geo_tracking_service.dto.LatestLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisLocationService {
    private final RedisTemplate<String, LatestLocation> redisTemplate;

    public RedisLocationService(
            RedisTemplate<String, LatestLocation> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveLatestLocation(LatestLocation location) {
        String key = "location:" + location.getUserId();

        redisTemplate.opsForValue().set(key, location);
    }

    public LatestLocation getLatestLocation(Long userId) {
        String key = "location:" + userId;

        return redisTemplate.opsForValue().get(key);
    }
}
