package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallSessionUsageDtoNew {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("peer_session_id")
    private String peerSessionId;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("imsi")
    private String imsi;

    @JsonProperty("called_msisdn")
    private String calledMsisdn;

    @JsonProperty("location_info")
    private String locationInfo;

    @JsonProperty("session_state")
    private Boolean sessionState;

    @JsonProperty("call_start_ts")
    private String callStartTs;

    @JsonProperty("call_end_ts")
    private String callEndTs;

    @JsonProperty("total_seconds")
    private Long totalSeconds;

    @JsonProperty("call_status")
    private Boolean callStatus;

	@Override
	public String toString() {
		return "id=" + id + ", peer_session_id=" + peerSessionId + ", msisdn=" + msisdn + ", imsi=" + imsi
				+ ", called_msisdn=" + calledMsisdn + ", location_info=" + locationInfo + ", session_state="
				+ sessionState + ", call_start_ts=" + callStartTs + ", call_end_ts=" + callEndTs + ", total_seconds="
				+ totalSeconds + ", call_status=" + callStatus;
	}
}
