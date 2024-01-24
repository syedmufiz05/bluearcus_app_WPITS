package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostpaidAccountsDtoNew {
	@JsonProperty("account_id")
	private Integer accountId;

	@JsonProperty("customer_id")
	private Integer customerId;

	@JsonProperty("calling_number")
	private String callingNumber;

	@JsonProperty("called_number")
	private String calledNumber;

	@JsonProperty("call_start")
	private String callStart;

	@JsonProperty("call_end")
	private String callEnd;

	@JsonProperty("call_duration")
	private Integer callDuration;

	@JsonProperty("call_Type")
	private String callType;

	@JsonProperty("data_octets_session_start")
	private String dataOctetsSessionStart;

	@JsonProperty("data_octets_session_end")
	private String dataOctetsSessionEnd;

	@JsonProperty("data_octets_session_consumed")
	private Integer dataOctetsSessionConsumed;

	@JsonProperty("sms_destination_number")
	private String smsDestinationNumber;

	@JsonProperty("sms_consumed_count")
	private Integer smsConsumedCount;

	@JsonProperty("sms_consumed_date")
	private String smsConsumedDate;

	@Override
	public String toString() {
		return "account_id=" + accountId + ", customer_id=" + customerId + ", calling_number=" + callingNumber
				+ ", called_number=" + calledNumber + ", call_start=" + callStart + ", call_end=" + callEnd
				+ ", call_duration=" + callDuration + ", call_Type=" + callType + ", data_octets_session_start="
				+ dataOctetsSessionStart + ", data_octets_session_end=" + dataOctetsSessionEnd
				+ ", data_octets_session_consumed=" + dataOctetsSessionConsumed + ", sms_destination_number="
				+ smsDestinationNumber + ", sms_consumed_count=" + smsConsumedCount + ", sms_consumed_date="
				+ smsConsumedDate;
	}
}
