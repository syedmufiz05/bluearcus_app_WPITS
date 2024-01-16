package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrepaidRoamingAccountsDto {
    @JsonProperty("roaming_account_id")
    private Integer roamingAccountId;

    @JsonProperty("roaming_customer_id")
    private Integer roamingCustomerId;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("roaming_cs_voice_call_seconds")
    private Integer roamingCsVoiceCallSeconds;

    @JsonProperty("roaming_4g_data_octets")
    private Integer roaming4gDataOctets;

    @JsonProperty("roaming_5g_data_octets")
    private Integer roaming5gDataOctets;

    @JsonProperty("roaming_volte_call_seconds")
    private Integer roamingVolteCallSeconds;

    @JsonProperty("roaming_total_data_octets_available")
    private Integer roamingTotalDataOctetsAvailable;

    @JsonProperty("roaming_total_data_octets_consumed")
    private Integer roamingTotalDataOctetsConsumed;

    @JsonProperty("roaming_total_call_seconds_available")
    private Integer roamingTotalCallSecondsAvailable;

    @JsonProperty("roaming_total_call_seconds_consumed")
    private Integer roamingTotalCallSecondsConsumed;

    @JsonProperty("roaming_total_sms_available")
    private Integer roamingTotalSmsAvailable;

    @JsonProperty("roaming_total_sms_consumed")
    private Integer roamingTotalSmsConsumed;
}
