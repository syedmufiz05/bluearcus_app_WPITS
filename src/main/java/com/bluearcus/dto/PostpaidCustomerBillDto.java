package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostpaidCustomerBillDto {
	@JsonProperty("pack_name")
	private String packName;

	@JsonProperty("pack_price")
	private Integer packPrice;

	@JsonProperty("extra_charges")
	private Integer extraCharges;

	@JsonProperty("total_amount")
	private Integer totalAmount;

	@JsonProperty("from_date")
	private String fromDate;

	@JsonProperty("to_date")
	private String toDate;

	@JsonProperty("pack_offered_data")
	private String packOfferedData;

	@JsonProperty("total_used_data")
	private String totalUsedData;

	@JsonProperty("pack_offered_calls")
	private String packOfferedCalls;

	@JsonProperty("total_used_calls")
	private String totalUsedCalls;

	@JsonProperty("pack_offered_sms")
	private Integer packOfferedSms;

	@JsonProperty("total_used_sms")
	private Long totalUsedSms;
}
