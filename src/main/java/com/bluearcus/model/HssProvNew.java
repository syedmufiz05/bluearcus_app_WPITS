package com.bluearcus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "hssprov_new")
@Data
public class HssProvNew {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "imsi")
    private String imsi;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "ambr")
    private String ambr;

    @Column(name = "nssai")
    private String nssai;

    @Column(name = "arfb")
    private String arfb;

    @Column(name = "sar")
    private String sar;

    @Column(name = "rat")
    private Integer rat;

    @Column(name = "cn")
    private Integer cn;

    @Column(name = "smf_sel")
    private String smfSel;

    @Column(name = "sm_dat")
    private String smDat;

    @Column(name = "eps_flag")
    private Boolean epsFlag;

    @Column(name = "eps_odb")
    private Integer epsOdb;

    @Column(name = "hplmn_odb")
    private Integer hplmnOdb;

    @Column(name = "ard")
    private Integer ard;

    @Column(name = "epstpl")
    private String epsTpl;

    @Column(name = "context_id")
    private Integer contextId;

    @Column(name = "apn_context")
    private String apnContext;
}
