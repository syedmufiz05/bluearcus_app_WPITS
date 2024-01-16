package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketMsgDto {

    @JsonProperty("imsi")
    private String imsi;

    @JsonProperty("imsi_flag")
    private Boolean imsiFlag;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("nam")
    private String nam;

    @JsonProperty("odb")
    private Boolean odb;

    @JsonProperty("baoc")
    private Boolean baoc;

    @JsonProperty("boic")
    private Boolean boic;

    @JsonProperty("osb1")
    private Boolean osb1;

    @JsonProperty("osb2")
    private Boolean osb2;

    @JsonProperty("baic")
    private Boolean baic;

    @JsonProperty("roaming")
    private Boolean roaming;

    @JsonProperty("bearer_service")
    private String bearerService;

    @JsonProperty("telephone")
    private Boolean telephone;

    @JsonProperty("sms")
    private String sms;

    @JsonProperty("cfu_a")
    private Boolean cfuA;

    @JsonProperty("cfu_r")
    private Boolean cfuR;

    @JsonProperty("cfu_p")
    private Boolean cfuP;

    @JsonProperty("cfb_p")
    private Boolean cfbP;

    @JsonProperty("cfnry_p")
    private Boolean cfnryP;

    @JsonProperty("cfnry_t")
    private Integer cfnryT;

    @JsonProperty("cfnrc_p")
    private Boolean cfnrcP;

    @JsonProperty("cw_a")
    private Boolean cwA;

    @JsonProperty("cw_p")
    private Boolean cwP;

    @JsonProperty("ch_p")
    private Boolean chP;

    @JsonProperty("camel")
    private Boolean camel;

    @JsonProperty("o_csi")
    private Boolean oCsi;

    @JsonProperty("t_csi")
    private Boolean TCsi;

    @JsonProperty("ss_csi")
    private Boolean ssCsi;

    @JsonProperty("sms_csi")
    private Boolean smsCsi;

    @JsonProperty("o_csi_scf_no")
    private String oCsiScfNo;

    @JsonProperty("t_csi_scf_no")
    private String tCsiScfNo;

    @JsonProperty("ss_csi_scf_no")
    private String ssCsiScfNo;

    @JsonProperty("sms_sci_scf_no")
    private String smsSciScfNo;

    @JsonProperty("gprs_flag")
    private Boolean gprsFlag;

    @JsonProperty("eps_flag")
    private Boolean epsFlag;

    @JsonProperty("ard")
    private String ard;

    @JsonProperty("eps_user_tpl")
    private String epsUserTpl;

    @JsonProperty("def_eps")
    private String defEps;

    @JsonProperty("context_d")
    private String contextD;

    @JsonProperty("apn_ctxt_list")
    private String apnCtxtList;

    @JsonProperty("ims_flag")
    private Boolean imsFlag;

}
