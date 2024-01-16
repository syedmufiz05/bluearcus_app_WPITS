package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMgmtDto {
    @JsonProperty("inventory_id")
    private Integer inventoryId;

    @JsonProperty("imsi")
    private String imsi;

    @JsonProperty("p_imsi")
    private String pImsi;

    @JsonProperty("batch_id")
    private Integer batchId;

    @JsonProperty("vendor_id")
    private Integer vendorId;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("prov_status")
    private Boolean provStatus;

    @JsonProperty("allocation_date")
    private String allocationDate;

    @JsonProperty("activation_date")
    private String activationDate;
}
