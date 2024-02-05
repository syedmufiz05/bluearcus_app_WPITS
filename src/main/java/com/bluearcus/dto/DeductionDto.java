package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionDto {
	@JsonProperty("msisdn")
	private String msisdn;

	@JsonProperty("imsi")
	private String imsi;

	@JsonProperty("called-station-id")
	private String calledStationId;
	
	@JsonProperty("action")
	private String action;

	@JsonProperty("qos-info")
	private String qosInfo;

	@JsonProperty("framed-ip")
	private String framedIp;

	@JsonProperty("pcc-rule")
	private String pccRule;

	@JsonProperty("monitoring-key")
	private String monitoringKey;

	@JsonProperty("consumed-octets")
	private ConsumeOctetsDto consumedOctets;

	@JsonProperty("consumed-time-seconds")
	private Long consumedTimeSeconds;

	@JsonProperty("consumed-sms")
	private Long consumedSms;
}
