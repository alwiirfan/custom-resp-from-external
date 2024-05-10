package com.alwi.rest_template.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VehicleResponse {
    private String plate;
    private String gsm_no;
    private String activation_time;
    private String expired_gsm;
    private String status;

    @JsonIgnore
    private Integer speed;

    @JsonIgnore
    private String acc;
}
