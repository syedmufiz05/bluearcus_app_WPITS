package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DestinationDto {
    @JsonProperty("destination_id")
    private Integer destinationId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("access_id")
    private Integer accessId;
}
