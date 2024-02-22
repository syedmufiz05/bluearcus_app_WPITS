package com.bluearcus.model;

import lombok.Data;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_management")
@Data
public class InventoryMgmt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Integer id;

    @Column(name = "imsi")
    private String imsi;

    @Column(name = "p_imsi")
    private String pImsi;

    @Column(name = "batch_id")
    private Integer batchId;

    @Column(name = "vendor_id")
    private Integer vendorId;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "prov_status")
    private Boolean provStatus;

    @Column(name = "allocation_date")
    private Date allocationDate;

    @Column(name = "activation_date")
    private Date activationDate;
}
