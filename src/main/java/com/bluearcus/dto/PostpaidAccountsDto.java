package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostpaidAccountsDto {
	@JsonProperty("account_id")
	private Integer accountId;

	@JsonProperty("customer_id")
	private Integer customerId;

	@JsonProperty("msisdn")
	private String msisdn;

	@JsonProperty("imsi")
	private String imsi;

	@JsonProperty("data_parameter_type")
	private String dataParameterType;

	@JsonProperty("cs_voice_call_seconds")
	private Long csVoiceCallSeconds;

	@JsonProperty("4g_data_octets")
	private Integer fourGDataOctets;

	@JsonProperty("5g_data_octets")
	private Integer fiveGDataOctets;

	@JsonProperty("volte_call_seconds")
	private Long volteCallSeconds;

	@JsonProperty("total_data_octets_available")
	private Long totalDataOctetsAvailable;

	@JsonProperty("total_input_data_octets_available")
	private Long totalInputDataOctetsAvailable;

	@JsonProperty("total_output_data_octets_available")
	private Long totalOutputDataOctetsAvailable;

	@JsonProperty("total_data_octets_consumed")
	private Long totalDataOctetsConsumed;

	@JsonProperty("total_call_seconds_available")
	private Long totalCallSecondsAvailable;

	@JsonProperty("total_call_seconds_consumed")
	private Long totalCallSecondsConsumed;

	@JsonProperty("total_sms_available")
	private Long totalSmsAvailable;

	@JsonProperty("total_sms_consumed")
	private Long totalSmsConsumed;

	@Override
	public String toString() {
		return accountId + " , " + customerId + " , " + msisdn + " , " + imsi + " , " + dataParameterType + " , "
				+ csVoiceCallSeconds + " , " + fourGDataOctets + " , " + fiveGDataOctets + " , " + volteCallSeconds
				+ " , " + totalDataOctetsAvailable + " , " + totalInputDataOctetsAvailable + " , "
				+ totalOutputDataOctetsAvailable + " , " + totalDataOctetsConsumed + " , " + totalCallSecondsAvailable
				+ " , " + totalCallSecondsConsumed + " , " + totalSmsAvailable + " , " + totalSmsConsumed;
	}
}
