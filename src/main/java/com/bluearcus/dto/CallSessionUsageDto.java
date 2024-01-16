package com.bluearcus.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallSessionUsageDto {
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
    private Date callStartTs;

    @JsonProperty("call_end_ts")
    private Date callEndTs;

    @JsonProperty("total_seconds")
    private Long totalSeconds;

    @JsonProperty("call_status")
    private Boolean callStatus;
}
