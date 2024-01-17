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

	@JsonProperty("sms_balance")
	public Integer smsBalance;

	@JsonProperty("data_balance")
	public Integer dataBalance;

	@JsonProperty("category_name_list")
	private List<String> categoryNameDtoList;

	@JsonProperty("rating_offer_list")
	private List<String> ratesOfferDtoList;

	@JsonProperty("rating_plan_list")
	private List<String> ratingPlanDtoList;
}
