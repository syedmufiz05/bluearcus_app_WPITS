package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatesOfferDto {
    @JsonProperty("rates_id")
    private Integer ratesId;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("price_type")
    private String priceType;

    @JsonProperty("period")
    private Integer period;

    @JsonProperty("description")
    private String description;
}
