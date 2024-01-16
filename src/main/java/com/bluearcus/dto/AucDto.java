package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AucDto {

    @JsonProperty("auc_id")
    private Integer aucId;

    @JsonProperty("imsi")
    private String imsi;

    @JsonProperty("ki")
    private String ki;

    @JsonProperty("opc")
    private String opc;

    @JsonProperty("a3a8_version")
    private Integer a3a8Version;

    @JsonProperty("status")
    private String status;

    @JsonProperty("access_id")
    private Integer accessId;

}
