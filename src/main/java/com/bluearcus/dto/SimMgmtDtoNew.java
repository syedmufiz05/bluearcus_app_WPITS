package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimMgmtDtoNew {
    @JsonProperty("sim_id")
    private Integer simId;

    @JsonProperty("imsi")
    private String imsi;

    @JsonProperty("batch_no")
    private String batchNo;

    @JsonProperty("batch_date")
    private String batchDate;

    @JsonProperty("allocation_date")
    private String allocationDate;

    @JsonProperty("sim_type")
    private String simType;

    @JsonProperty("key_id")
    private Integer keyId;

    @JsonProperty("auth_id")
    private Integer authId;

    @JsonProperty("vendor_name")
    private String vendorName;

    @JsonProperty("status")
    private Boolean status;
}
