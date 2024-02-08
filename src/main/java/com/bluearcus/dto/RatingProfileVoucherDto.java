package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

	@JsonProperty("sms_balance")
	public Integer smsBalance;

	@JsonProperty("data_balance")
	public Integer dataBalance;
	
	@JsonProperty("data_balance_parameter")
	public String dataBalanceParameter;

	@JsonProperty("category_name")
	private String categoryName;

	@JsonProperty("rates_offer")
	private String ratesOffer;
}
