package com.bluearcus.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "device_management")
@Data
public class DeviceMgmt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "imei_primary")
    private String imeiPrimary;

    @Column(name = "imei_list")
    private String imeiList;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "foot_print")
    private String footPrint;

    @Column(name = "eir_track_id")
    private Integer eirTrackId;

    @Column(name = "is_esim")
    private Boolean isESim;

    @Column(name = "is_uicc")
    private Boolean isUicc;

    @Column(name = "registration_date")
    @CreationTimestamp
    private Date registrationDate;

    @Column(name = "status")
    private Boolean status;
}
