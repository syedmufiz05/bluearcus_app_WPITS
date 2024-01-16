package com.bluearcus.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "postpaid_accounts")
@Data
public class PostpaidAccounts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id")
	private Integer accountId;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "calling_number")
	private String callingNumber;

	@Column(name = "called_number")
	private String calledNumber;

	@CreationTimestamp
	@Column(name = "call_start")
	private Date callStart;

	@CreationTimestamp
	@Column(name = "call_end")
	private Date callEnd;

	@Column(name = "call_duration")
	private Integer callDuration;

	@Column(name = "call_Type")
	private String callType;

	@CreationTimestamp
	@Column(name = "data_octets_session_start")
	private Date dataOctetsSessionStart;

	@CreationTimestamp
	@Column(name = "data_octets_session_end")
	private Date dataOctetsSessionEnd;

	@Column(name = "data_octets_session_consumed")
	private Integer dataOctetsSessionConsumed;

	@Column(name = "sms_destination_number")
	private String smsDestinationNumber;

	@Column(name = "sms_consumed_count")
	private Integer smsConsumedCount;

	@CreationTimestamp
	@Column(name = "sms_consumed_date")
	private Date smsConsumedDate;
}
