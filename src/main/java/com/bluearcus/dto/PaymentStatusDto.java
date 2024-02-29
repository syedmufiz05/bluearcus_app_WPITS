package com.bluearcus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentStatusDto {

	@JsonProperty("customer_id")
	private Integer customerId;

	@JsonProperty("payment_status")
	private Boolean paymentStatus;

}
