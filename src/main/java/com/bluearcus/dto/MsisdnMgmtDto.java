package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsisdnMgmtDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("category")
    private String category;

    @JsonProperty("series_id")
    private Integer seriesId;

    @JsonProperty("is_prepaid")
    private Boolean isPrepaid;

    @JsonProperty("is_postpaid")
    private Boolean isPostpaid;

    @JsonProperty("is_m2m")
    private Boolean isM2M;

    @JsonProperty("is_special_no")
    private Boolean isSpecialNo;

    @JsonProperty("allocation_date")
    private Date allocationDate;

    @JsonProperty("status")
    private Boolean status;
}
