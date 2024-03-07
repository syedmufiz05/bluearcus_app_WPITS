package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrepaidDataBalanceDto {
    @JsonProperty("total_data_octets_available")
    private Long totalDataOctetsAvailable;

    @JsonProperty("total_input_data_octets_available")
    private Long totalInputDataOctetsAvailable;

    @JsonProperty("total_output_data_octets_available")
    private Long totalOutputDataOctetsAvailable;
}
