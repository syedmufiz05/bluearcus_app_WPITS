package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingProfileVoucherDto {
	@JsonProperty("rating_profile_id")
	private Integer ratingProfileId;

	@JsonProperty("pack_name")
	public String packName;

	@JsonProperty("pack_type")
	public String packType;

	@JsonProperty("call_balance")
	public Integer callBalance;

	@JsonProperty("call_balance_parameter")
	public String callBalanceParameter;

	@JsonProperty("assigned_call_balance")
	public String assignedCallBalance;

	@JsonProperty("sms_balance")
	public Integer smsBalance;

	@JsonProperty("data_balance")
	public Integer dataBalance;

	@JsonProperty("data_balance_parameter")
	public String dataBalanceParameter;

	@JsonProperty("assigned_data_balance")
	public String assignedDataBalance;

	@JsonProperty("category_name")
	private String categoryName;

	@JsonProperty("rates_offer")
	private String ratesOffer;
}
