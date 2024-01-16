package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorMgmtDto {
    @JsonProperty("vendor_id")
    private Integer vendorId;

    @JsonProperty("vendor_name")
    private String vendorName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("address")
    private String address;

    @JsonProperty("type")
    private String type;

    @JsonProperty("identification")
    private String identification;

    @JsonProperty("batch_prefix")
    private String batchPrefix;

    @JsonProperty("registration_date")
    private Date registrationDate;

    @JsonProperty("status")
    private Boolean status;
}
