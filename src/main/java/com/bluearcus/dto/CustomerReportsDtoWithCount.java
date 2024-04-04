package com.bluearcus.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReportsDtoWithCount {
	@JsonProperty("customer_reports")
	private List<CustomerReportsDto> customerReports;

	@JsonProperty("customer_count")
	private Integer customerCount;
}
