package com.bluearcus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rating_profile_voucher")
public class RatingProfileVoucher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "pack_name")
	private String packName;

	@Column(name = "pack_type")
	private String packType;
	
	@Column(name = "pack_for")
	private String packFor;
	
	@Column(name = "is_flexible_plan")
	private Boolean isFlexiblePlan;

	@Column(name = "call_balance")
	private Integer callBalance;

	@Column(name = "call_balance_parameter")
	private String callBalanceParameter;

	@Column(name = "assigned_call_balance")
	private String assignedCallBalance;

	@Column(name = "sms_balance")
	private Integer smsBalance;

	@Column(name = "data_balance")
	private Integer dataBalance;

	@Column(name = "data_balance_parameter")
	private String dataBalanceParameter;

	@Column(name = "assigned_data_balance")
	private String assignedDataBalance;

	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "rates_offer")
	private String ratesOffer;
}
