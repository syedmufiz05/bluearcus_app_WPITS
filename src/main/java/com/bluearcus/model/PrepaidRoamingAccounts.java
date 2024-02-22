package com.bluearcus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "prepaid_roaming_accounts")
public class PrepaidRoamingAccounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roaming_account_id")
    private Integer roamingAccountId;

    @Column(name = "roaming_customer_id")
    private Integer roamingCustomerId;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "roaming_cs_voice_call_seconds")
    private Integer roamingCsVoiceCallSeconds;

    @Column(name = "roaming_4g_data_octets")
    private Integer roaming4gDataOctets;

    @Column(name = "roaming_5g_data_octets")
    private Integer roaming5gDataOctets;

    @Column(name = "roaming_volte_call_seconds")
    private Integer roamingVolteCallSeconds;

    @Column(name = "roaming_total_data_octets_available")
    private Integer roamingTotalDataOctetsAvailable;

    @Column(name = "roaming_total_data_octets_consumed")
    private Integer roamingTotalDataOctetsConsumed;

    @Column(name = "roaming_total_call_seconds_available")
    private Integer roamingTotalCallSecondsAvailable;

    @Column(name = "roaming_total_call_seconds_consumed")
    private Integer roamingTotalCallSecondsConsumed;

    @Column(name = "roaming_total_sms_available")
    private Integer roamingTotalSmsAvailable;

    @Column(name = "roaming_total_sms_consumed")
    private Integer roamingTotalSmsConsumed;
}
