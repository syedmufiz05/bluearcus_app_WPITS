package com.bluearcus.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "hssprov_record")
@Data
public class HssProv {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hssprov_id")
    private Integer hssprovId;

    @Column(name = "imsi")
    private String imsi;

    @Column(name = "imsi_flag")
    private Boolean imsiFlag;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "nam")
    private String nam;

    @Column(name = "odb")
    private Boolean odb;

    @Column(name = "baoc")
    private Boolean baoc;

    @Column(name = "boic")
    private Boolean boic;

    @Column(name = "osb1")
    private Boolean osb1;

    @Column(name = "osb2")
    private Boolean osb2;

    @Column(name = "baic")
    private Boolean baic;

    @Column(name = "roaming")
    private Boolean roaming;

    @Column(name = "bearer_service")
    private String bearerService;

    @Column(name = "telephone")
    private Boolean telephone;

    @Column(name = "sms")
    private String sms;

    @Column(name = "cfu_a")
    private Boolean cfuA;

    @Column(name = "cfu_r")
    private Boolean cfuR;

    @Column(name = "cfu_p")
    private Boolean cfuP;

    @Column(name = "cfb_p")
    private Boolean cfbP;

    @Column(name = "cfnry_p")
    private Boolean cfnryP;

    @Column(name = "cfnry_t")
    private Integer cfnryT;

    @Column(name = "cfnrc_p")
    private Boolean cfnrcP;

    @Column(name = "cw_a")
    private Boolean cwA;

    @Column(name = "cw_p")
    private Boolean cwP;

    @Column(name = "ch_p")
    private Boolean chP;

    @Column(name = "camel")
    private Boolean camel;

    @Column(name = "o_csi")
    private Boolean oCsi;

    @Column(name = "t_csi")
    private Boolean tCsi;

    @Column(name = "ss_csi")
    private Boolean ssCsi;

    @Column(name = "sms_csi")
    private Boolean smsCsi;

    @Column(name = "o_csi_scf_no")
    private String oCsiScfNo;

    @Column(name = "t_csi_scf_no")
    private String tCsiScfNo;

    @Column(name = "ss_csi_scf_no")
    private String ssCsiScfNo;

    @Column(name = "sms_sci_scf_no")
    private String smsSciScfNo;

    @Column(name = "gprs_flag")
    private Boolean gprsFlag;

    @Column(name = "eps_flag")
    private Boolean epsFlag;

    @Column(name = "ard")
    private String ard;

    @Column(name = "eps_user_tpl")
    private String epsUserTpl;

    @Column(name = "def_eps")
    private String defEps;

    @Column(name = "context_d")
    private String contextD;

    @Column(name = "apn_ctxt_list")
    private String apnCtxtList;

    @Column(name = "ims_flag")
    private Boolean imsFlag;

    @Column(name = "subscriber_prof_id")
    private Integer subscriberProfId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "access_id", referencedColumnName = "id")
    private AccessLogs accessLogs;
}
