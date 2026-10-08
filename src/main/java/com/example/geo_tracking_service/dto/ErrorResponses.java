package com.example.geo_tracking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponses {
    private LocalDateTime localDateTime;
    private int status;
    private String message;
    private Map<String,String> errors;

}
