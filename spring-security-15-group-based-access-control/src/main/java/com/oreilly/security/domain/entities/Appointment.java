package com.oreilly.security.domain.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.oreilly.security.domain.entities.util.LocalDateConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "APPOINTMENT")
@EqualsAndHashCode(exclude = { "services" })
@ToString(exclude = { "services" })
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APPOINTMENT_ID")
	private Long appointmentId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private AutoUser user;

	@Embedded
	private Automobile automobile;

	@Column(name = "APPOINTMENT_DT")
	@Convert(converter = LocalDateConverter.class)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate appointmentDt;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "SERVICES", joinColumns = { @JoinColumn(name = "APPOINTMENT_ID") })
	@Column(name = "NAME")
	private List<String> services = new ArrayList<String>();

	@Column(name = "STATUS")
	private String status;

}
