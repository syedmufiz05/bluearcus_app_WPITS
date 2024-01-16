package com.bluearcus.dto;

import java.util.Date;

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

	@JsonProperty("calling_number")
	private String callingNumber;

	@JsonProperty("called_number")
	private String calledNumber;

	@JsonProperty("call_start")
	private Date callStart;

	@JsonProperty("call_end")
	private Date callEnd;

	@JsonProperty("call_duration")
	private Integer callDuration;

	@JsonProperty("call_Type")
	private String callType;

	@JsonProperty("data_octets_session_start")
	private Date dataOctetsSessionStart;

	@JsonProperty("data_octets_session_end")
	private Date dataOctetsSessionEnd;

	@JsonProperty("data_octets_session_consumed")
	private Integer dataOctetsSessionConsumed;

	@JsonProperty("sms_destination_number")
	private String smsDestinationNumber;

	@JsonProperty("sms_consumed_count")
	private Integer smsConsumedCount;

	@JsonProperty("sms_consumed_date")
	private Date smsConsumedDate;
}
