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
@Table(name = "auc_record")
@Data
public class Auc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auc_id")
	private Integer aucId;

	@Column(name = "imsi")
	private String imsi;

	@Column(name = "ki")
	private String ki;

	@Column(name = "opc")
	private String opc;

	@Column(name = "a3a8_version")
	private Integer a3a8Version;

	@Column(name = "status")
	private String status;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "access_id", referencedColumnName = "id")
	@JsonProperty("access_id")
	private AccessLogs accessLogs;

}
