package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DestinationRatesDto {
    @JsonProperty("destination_rates_id")
    private Integer destinationRatesId;

    @JsonProperty("destination_id")
    private Integer destinationId;

    @JsonProperty("rates_tag")
    private Integer ratesTag;
}
