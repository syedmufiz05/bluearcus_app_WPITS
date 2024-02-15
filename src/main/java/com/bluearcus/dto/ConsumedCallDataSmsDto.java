package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumedCallDataSmsDto {
	@JsonProperty("total_consumed_data")
	private Long totalConsumedData;
	
	@JsonProperty("total_consumed_call")
	private Long totalConsumedCall;
	
	@JsonProperty("total_consumed_sms")
	private Integer totalConsumedSms;
}
