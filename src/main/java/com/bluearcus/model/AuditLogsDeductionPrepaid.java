package com.bluearcus.model;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "audit_logs_deduction_prepaid")
@Data
public class AuditLogsDeductionPrepaid {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "req_payload", columnDefinition = "JSON")
	private String reqPayload;
	
	@Column(name = "msisdn")
	private String msisdn;

	public void setReqPayload(String reqPayload) {
		try {
			this.reqPayload = new ObjectMapper().writeValueAsString(reqPayload);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
