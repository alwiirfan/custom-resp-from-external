package com.alwi.rest_template.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alwi.rest_template.dto.response.VehicleResponse;
import com.alwi.rest_template.service.VehicleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final RestTemplate restTemplate;

    @Value("${EXTERNAL_URL}")
    private String externalUrl;

    // @Value("${EXTERNAL_TOKEN}")
    // private String token;

    @Override
    public List<Map<String, Object>> getVehicles(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);

        // option-2 for bearer token using ${EXTERNAL_TOKEN}
        // headers.setBearerAuth(token);

        ResponseEntity<Map<String, Object>> responseVehicle = restTemplate
                .exchange(externalUrl, HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<Map<String, Object>>() {

                        });

        System.out.println(responseVehicle.getBody().get("message"));

        if (responseVehicle.getStatusCode().is2xxSuccessful() && responseVehicle.getBody() != null) {
            List<Map<String, Object>> vehicles = Arrays.asList(responseVehicle.getBody());
            return vehicles;
        }

        return Collections.emptyList();
    }

    @Override
    public List<VehicleResponse> getVehiclesQuestion(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);

        // option-2 for bearer token using ${EXTERNAL_TOKEN}
        // headers.setBearerAuth(token);

        System.out.println(headers);

        try {
            ResponseEntity<Map<String, Object>> responseVehicle = restTemplate
                    .exchange(externalUrl, HttpMethod.GET,
                            new HttpEntity<>(headers),
                            new ParameterizedTypeReference<Map<String, Object>>() {
                            });

            System.out.println(responseVehicle.getBody().get("message"));

            Map<String, Object> vehicleMessage = (Map<String, Object>) responseVehicle.getBody().get("message");

            System.out.println(vehicleMessage.get("data"));

            List<VehicleResponse> vehicles = new ArrayList<>();

            if (responseVehicle.getStatusCode().is2xxSuccessful() && responseVehicle.getBody() != null) {

                List<Map<String, Object>> vehiclesData = (List<Map<String, Object>>) vehicleMessage.get("data");

                for (Map<String, Object> vehicle : vehiclesData) {
                    VehicleResponse vehicleResponse = VehicleResponse.builder()
                            .plate((String) vehicle.get("plate"))
                            .gsm_no((String) vehicle.get("gsm_no"))
                            .activation_time((String) vehicle.get("activation_time"))
                            .expired_gsm((String) vehicle.get("expired_gsm"))
                            .status(getStatus((String) vehicle.get("acc"), (Integer) vehicle.get("speed")))
                            .build();

                    vehicles.add(vehicleResponse);
                }

                return vehicles;

            } else {
                System.out.println("Failed to get vehicles from API: " + responseVehicle.getStatusCode());
                return Collections.emptyList();
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error while communicating with API: ", e.getMessage());
            return Collections.emptyList();
        } catch (RestClientException e) {
            System.out.println("Error while communicating with API: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private String getStatus(String acc, int speed) {
        if ("ON".equals(acc) && speed > 0) {
            return "Running";
        } else if ("OFF".equals(acc) && speed == 0) {
            return "Parking";
        } else if ("ON".equals(acc) && speed == 0) {
            return "Stopped";
        } else {
            return "Unknown";
        }
    }
}
