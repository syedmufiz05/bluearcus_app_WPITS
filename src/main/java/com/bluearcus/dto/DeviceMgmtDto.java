package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceMgmtDto {
    @JsonProperty("device_id")
    private Integer deviceId;

    @JsonProperty("imei_primary")
    private String imeiPrimary;

    @JsonProperty("imei_list")
    private String imeiList;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("foot_print")
    private String footPrint;

    @JsonProperty("eir_track_id")
    private Integer eirTrackId;

    @JsonProperty("is_esim")
    private Boolean isESim;

    @JsonProperty("is_uicc")
    private Boolean isUicc;

    @JsonProperty("registration_date")
    private Date registrationDate;

    @JsonProperty("status")
    private Boolean status;
}
