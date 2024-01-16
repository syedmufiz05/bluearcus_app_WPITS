package com.bluearcus.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "data_session_usage")
@Data
public class DataSessionUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "peer_session_id")
    private String peerSessionId;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "imsi")
    private String imsi;

    @Column(name = "framed_ip")
    private String framedIp;

    @Column(name = "location_info")
    private String locationInfo;

    @Column(name = "sgsn_address")
    private Boolean sgsnAddress;

    @Column(name = "called_station_id")
    private String calledStationId;

    @Column(name = "session_state")
    private Boolean sessionState;

    @CreationTimestamp
    @Column(name = "session_start_ts")
    private Date sessionStartTs;

    @CreationTimestamp
    @Column(name = "session_end_ts")
    private Date sessionEndTs;

    @Column(name = "total_octates")
    private Long totalOctates;

    @Column(name = "bitrate")
    private Long bitrate;

    @Column(name = "total_input_octets")
    private Long totalInputOctets;

    @Column(name = "total_output_octets")
    private Long totalOutputOctets;

    @Column(name = "session_status")
    private Boolean sessionStatus;
}

