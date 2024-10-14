package com.bluearcus.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReportsDto {
	@JsonProperty("id")
	private Integer id;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("ekyc_status")
	private String ekycStatus;

	@JsonProperty("ekyc_token")
	private String ekycToken;

	@JsonFormat(pattern = "yyyy-MMM-dd HH:mm:ss")
	@JsonProperty("ekyc_date")
	private LocalDateTime ekycDate;

	@JsonProperty("customer_id")
	private Integer customerId;

	@JsonProperty("customer_type")
	private String customerType;

	@Pattern(regexp = "\\d+", message = "MSISDN accepts only digits")
	@JsonProperty("msisdn")
	private String msisdn;

	@JsonProperty("imsi")
	@Pattern(regexp = "\\d+", message = "IMSI accepts only digits")
	@Size(min = 15, max = 15, message = "IMSI length should be 15")
	private String imsi;
	
	@JsonProperty("pack_id")
	private Integer packId;
	
	@JsonProperty("pack_name")
	private String packName;

	@JsonProperty("payment_status")
	private Boolean paymentStatus;
}
