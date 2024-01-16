package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatesPlanOfferDto {
    @JsonProperty("rates_plan_offer_id")
    private Integer ratesPlanOfferId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("period")
    private Integer period;

    @JsonProperty("description")
    private String description;

    @JsonProperty("active")
    private Boolean active;
}
