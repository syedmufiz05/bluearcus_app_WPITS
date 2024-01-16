package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingProfileDto {
    @JsonProperty("rating_profile_id")
    private Integer ratingProfileId;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("calling_party")
    private String callingParty;

    @JsonProperty("rating_plan_id")
    private Integer ratingPlanId;
}
