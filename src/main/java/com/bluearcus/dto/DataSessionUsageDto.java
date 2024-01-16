package com.bluearcus.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSessionUsageDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("peer_session_id")
    private String peerSessionId;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("imsi")
    private String imsi;

    @JsonProperty("framed_ip")
    private String framedIp;

    @JsonProperty("location_info")
    private String locationInfo;

    @JsonProperty("sgsn_address")
    private Boolean sgsnAddress;

    @JsonProperty("called_station_id")
    private String calledStationId;

    @JsonProperty("session_state")
    private Boolean sessionState;

    @JsonProperty("session_start_ts")
    private Date sessionStartTs;

    @JsonProperty("session_end_ts")
    private Date sessionEndTs;

    @JsonProperty("total_octates")
    private Long totalOctates;

    @JsonProperty("bitrate")
    private Long bitrate;

    @JsonProperty("total_input_octets")
    private Long totalInputOctets;

    @JsonProperty("total_output_octets")
    private Long totalOutputOctets;

    @JsonProperty("session_status")
    private Boolean sessionStatus;
}
