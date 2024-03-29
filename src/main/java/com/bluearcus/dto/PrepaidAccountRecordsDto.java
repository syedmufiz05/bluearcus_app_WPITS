package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrepaidAccountRecordsDto {
	@JsonProperty("date")
	private String date;

	@JsonProperty("msisdn")
	private String msisdn;

	@JsonProperty("account_detail")
	private PrepaidAccountsDto prepaidAccountsDto;
}
