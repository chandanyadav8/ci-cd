package com.example.geo_tracking_service.config;

import com.example.geo_tracking_service.dto.LatestLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, LatestLocation> redisTemplate(
            RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, LatestLocation> template =
                new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);

        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        Jackson2JsonRedisSerializer<LatestLocation> serializer =
                new Jackson2JsonRedisSerializer<>(
                        objectMapper,
                        LatestLocation.class
                );

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();

        return template;
    }
}