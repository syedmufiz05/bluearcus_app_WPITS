package com.bluearcus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "vms_record")
@Data
public class Vms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vms_id")
    private Integer vmsId;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "system_id")
    private Integer systemId;

    @Column(name = "mailbox_id")
    private Integer mailboxId;

    @Column(name = "register_flag")
    private Boolean registerFlag;

    @Column(name = "active_flag")
    private Boolean activeFlag;

    @Column(name = "locked_flag")
    private Boolean lockedFlag;

    @Column(name = "language")
    private Integer language;

    @Column(name = "temporary_greeting")
    private Boolean temporaryGreeting;

    @Column(name = "greeting_type_system")
    private String greetingTypeSystem;

    @Column(name = "password_flag")
    private Boolean passwordFlag;

    @Column(name = "callback_flag")
    private Boolean callbackFlag;

    @Column(name = "cli_flag")
    private Boolean cliFlag;

    @Column(name = "password")
    private String password;

    @Column(name = "callback_timeout")
    private String callbackTimeout;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "access_id", referencedColumnName = "id")
    @JsonProperty("access_id")
    private AccessLogs accessLogs;

}
