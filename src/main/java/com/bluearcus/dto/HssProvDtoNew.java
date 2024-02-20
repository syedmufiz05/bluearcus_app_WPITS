package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HssProvDtoNew {
    @JsonProperty("hss_prov_id")
    private Integer hssProvId;

    @JsonProperty("imsi")
    private String imsi;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("ambr")
    private String ambr;

    @JsonProperty("nssai")
    private String nssai;

    @JsonProperty("arfb")
    private String arfb;

    @JsonProperty("sar")
    private String sar;

    @JsonProperty("rat")
    private Integer rat;

    @JsonProperty("cn")
    private Integer cn;

    @JsonProperty("smf_sel")
    private String smfSel;

    @JsonProperty("sm_dat")
    private String smDat;

    @JsonProperty("eps_flag")
    private Boolean epsFlag;

    @JsonProperty("eps_odb")
    private Integer epsOdb;

    @JsonProperty("hplmn_odb")
    private Integer hplmnOdb;

    @JsonProperty("ard")
    private Integer ard;

    @JsonProperty("epstpl")
    private String epsTpl;

    @JsonProperty("context_id")
    private Integer contextId;

    @JsonProperty("apn_context")
    private String apnContext;
    
    @JsonProperty("access_id")
    private Integer accessId;
}
