package com.alwi.rest_template.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alwi.rest_template.dto.response.CommonResponse;
import com.alwi.rest_template.dto.response.Message;
import com.alwi.rest_template.dto.response.VehicleResponse;
import com.alwi.rest_template.service.VehicleService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping(name = "/external", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicles(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(vehicleService.getVehicles(token));
    }

    @GetMapping(path = "/question", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehiclesQuestion(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        System.out.println(token);
        List<VehicleResponse> vehicleResponses = vehicleService.getVehiclesQuestion(token);

        if (!vehicleResponses.isEmpty()) {
            CommonResponse<?> commonResponse = CommonResponse.builder()
                    .status(true)
                    .message(Message.builder()
                            .total(vehicleResponses.size())
                            .data(vehicleResponses)
                            .build())
                    .build();

            return ResponseEntity.ok(commonResponse);
        } else {
            CommonResponse<VehicleResponse> commonResponse = CommonResponse.<VehicleResponse>builder()
                    .status(false)
                    .build();

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
        }
    }
}
