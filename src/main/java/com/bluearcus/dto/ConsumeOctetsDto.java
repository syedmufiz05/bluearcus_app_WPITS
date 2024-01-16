package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumeOctetsDto {
    @JsonProperty("total")
    private Long total;

    @JsonProperty("input")
    private Long input;

    @JsonProperty("output")
    private Long output;
}
