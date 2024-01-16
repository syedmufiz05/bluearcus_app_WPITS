package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VmsDto {

    @JsonProperty("vms_id")
    private Integer vmsId;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("system_id")
    private Integer systemId;

    @JsonProperty("mailbox_id")
    private Integer mailboxId;

    @JsonProperty("register_flag")
    private Boolean registerFlag;

    @JsonProperty("active_flag")
    private Boolean activeFlag;

    @JsonProperty("locked_flag")
    private Boolean lockedFlag;

    @JsonProperty("language")
    private Integer language;

    @JsonProperty("temporary_greeting")
    private Boolean temporaryGreeting;

    @JsonProperty("greeting_type_system")
    private String greetingTypeSystem;

    @JsonProperty("password_flag")
    private Boolean passwordFlag;

    @JsonProperty("callback_flag")
    private Boolean callbackFlag;

    @JsonProperty("cli_flag")
    private Boolean cliFlag;

    @JsonProperty("password")
    private String password;

    @JsonProperty("callback_timeout")
    private String callbackTimeout;

    @JsonProperty("access_id")
    private Integer accessId;

}
