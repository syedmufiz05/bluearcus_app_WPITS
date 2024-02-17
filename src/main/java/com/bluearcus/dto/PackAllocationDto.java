package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackAllocationDto {
	@JsonProperty("pack_allocation_id")
	private Integer id;

	@JsonProperty("msisdn")
	private String msisdn;

	@JsonProperty("imsi")
	private String imsi;

	@JsonProperty("activation_date")
	private String activationDate;

	@JsonProperty("expiration_date")
	private String expirationDate;

	@JsonProperty("pack_id")
	private Integer packId;

	@JsonProperty("customer_id")
	private Integer customerId;
}
