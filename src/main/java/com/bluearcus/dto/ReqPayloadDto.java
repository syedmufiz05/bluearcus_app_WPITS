package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqPayloadDto {

    @JsonProperty("hssprov_id")
    private Integer hssprovId;

    @JsonProperty("imsi")
    private String imsi;

    @JsonProperty("imsi_flag")
    private Integer imsiFlag;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("nam")
    private String nam;

    @JsonProperty("odb")
    private Integer odb;

    @JsonProperty("baoc")
    private Integer baoc;

    @JsonProperty("boic")
    private Integer boic;

    @JsonProperty("osb1")
    private Integer osb1;

    @JsonProperty("osb2")
    private Integer osb2;

    @JsonProperty("baic")
    private Integer baic;

    @JsonProperty("roaming")
    private Integer roaming;

    @JsonProperty("bearer_service")
    private String bearerService;

    @JsonProperty("telephone")
    private Integer telephone;

    @JsonProperty("sms")
    private String sms;

    @JsonProperty("cfu_a")
    private Integer cfuA;

    @JsonProperty("cfu_r")
    private Integer cfuR;

    @JsonProperty("cfu_p")
    private Integer cfuP;

    @JsonProperty("cfb_p")
    private Integer cfbP;

    @JsonProperty("cfnry_p")
    private Integer cfnryP;

    @JsonProperty("cfnry_t")
    private Integer cfnryT;

    @JsonProperty("cfnrc_p")
    private Integer cfnrcP;

    @JsonProperty("cw_a")
    private Integer cwA;

    @JsonProperty("cw_p")
    private Integer cwP;

    @JsonProperty("ch_p")
    private Integer chP;

    @JsonProperty("camel")
    private Integer camel;

    @JsonProperty("o_csi")
    private Integer oCsi;

    @JsonProperty("t_csi")
    private Integer TCsi;

    @JsonProperty("ss_csi")
    private Integer ssCsi;

    @JsonProperty("sms_csi")
    private Integer smsCsi;

    @JsonProperty("o_csi_scf_no")
    private String oCsiScfNo;

    @JsonProperty("t_csi_scf_no")
    private String tCsiScfNo;
    @JsonProperty("ss_csi_scf_no")
    private String ssCsiScfNo;

    @JsonProperty("sms_sci_scf_no")
    private String smsSciScfNo;

    @JsonProperty("gprs_flag")
    private Integer gprsFlag;

    @JsonProperty("eps_flag")
    private Integer epsFlag;

    @JsonProperty("ard")
    private String ard;

    @JsonProperty("eps_user_tpl")
    private String epsUserTpl;

    @JsonProperty("context_d")
    private String contextD;

    @JsonProperty("apn_ctxt_list")
    private String apnCtxtList;

    @JsonProperty("ims_flag")
    private Integer imsFlag;

    @JsonProperty("subscriber_prof_id")
    private Integer subscriberProfId;

    @JsonProperty("access_id")
    private Integer accessId;

}
