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
@Table(name = "msisdn_management")
@Data
public class MsisdnMgmt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "category")
    private String category;

    @Column(name = "series_id")
    private Integer seriesId;

    @Column(name = "is_prepaid")
    private Boolean isPrepaid;

    @Column(name = "is_postpaid")
    private Boolean isPostpaid;

    @Column(name = "is_m2m")
    private Boolean isM2M;

    @Column(name = "is_special_no")
    private Boolean isSpecialNo;

    @Column(name = "allocation_date")
    @CreationTimestamp
    private Date allocationDate;

    @Column(name = "status")
    private Boolean status;

}
