package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrmAccountsDto {
	@JsonProperty("crm_account_id")
	private Integer crmAccountId;

	@JsonProperty("customer_id")
	private Integer customerId;

	@JsonProperty("customer_type")
	private String customerType;

	@JsonProperty("msisdn")
	private String msisdn;

	@JsonProperty("imsi")
	private String imsi;
}
