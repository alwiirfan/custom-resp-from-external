package com.alwi.rest_template.service;

// import org.springframework.data.domain.Page;

import com.alwi.rest_template.dto.response.VehicleResponse;

import java.util.List;
import java.util.Map;

public interface VehicleService {
    List<VehicleResponse> getVehiclesQuestion(String token);

    List<Map<String, Object>> getVehicles(String token);
}