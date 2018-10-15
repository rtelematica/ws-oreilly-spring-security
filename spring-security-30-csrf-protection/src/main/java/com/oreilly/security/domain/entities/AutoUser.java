package com.oreilly.security.domain.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Data
@Entity
@Table(name = "AUTO_USER")
@EqualsAndHashCode(exclude = { "appointments" })
@ToString(exclude = { "appointments" })
public class AutoUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long autoUserId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ROLE")
	private String role;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private @Getter(AccessLevel.NONE) List<Appointment> appointments = new ArrayList<Appointment>();

}
