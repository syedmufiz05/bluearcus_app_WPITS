package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VouchersDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("activated_date")
    private String activatedDate;

    @JsonProperty("amount")
    private Float amount;

    @JsonProperty("batch_id")
    private String batchId;

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("expiry_date")
    private String expiryDate;

    @JsonProperty("group_code")
    private String groupCode;

    @JsonProperty("instruction_id")
    private String instructionId;

    @JsonProperty("payee_functional_id")
    private String payeeFunctionalId;

    @JsonProperty("registering_institution_id")
    private String registeringInstitutionId;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("serial_no")
    private String serialNo;

    @JsonProperty("status")
    private String status;

    @JsonProperty("voucher_no")
    private String voucherNo;
}
