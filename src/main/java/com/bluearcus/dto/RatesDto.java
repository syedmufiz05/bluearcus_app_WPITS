package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatesDto {
    @JsonProperty("rates_id")
    private Integer ratesId;

    @JsonProperty("dest_name")
    private String destName;

    @JsonProperty("dest_type")
    private String destType;

    @JsonProperty("index")
    private Integer index;

    @JsonProperty("description")
    private String description;

    @JsonProperty("is_rates_active")
    private Boolean isRatesActive;

    @JsonProperty("access_id")
    private Integer accessId;
}
