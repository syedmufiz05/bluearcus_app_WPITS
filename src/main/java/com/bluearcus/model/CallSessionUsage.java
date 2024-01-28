package com.bluearcus.model;

import lombok.Data;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "call_session_usage")
@Data
public class CallSessionUsage {
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

    @Column(name = "called_msisdn")
    private String calledMsisdn;

    @Column(name = "location_info")
    private String locationInfo;

    @Column(name = "session_state")
    private Boolean sessionState;

    @CreationTimestamp
    @Column(name = "call_start_ts")
    private Date callStartTs;

    @Column(name = "call_end_ts")
    private Date callEndTs;

    @Column(name = "total_seconds")
    private Long totalSeconds;

    @Column(name = "call_status")
    private Boolean callStatus;
}
