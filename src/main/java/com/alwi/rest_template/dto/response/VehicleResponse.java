package com.alwi.rest_template.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonPropertyOrder({ "plate", "gsm_no", "activation_time", "expired_gsm", "status" })
public class VehicleResponse {

    private String plate;

    @JsonProperty("gsm_no")
    private String gsmNo;

    @JsonProperty("activation_time")
    private String activationTime;

    @JsonProperty("expired_gsm")
    private String expiredGsm;

    private String status;

    @JsonIgnore
    private Integer speed;

    @JsonIgnore
    private String acc;
}
