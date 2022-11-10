package com.aj.diningreview.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Name is mandatory")
	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "zip")
	private String zipCode;

	@Column(name = "peanutAllergy")
	private boolean peanutAllergiesInterested;

	@Column(name = "eggAllergy")
	private boolean eggAllergiesInterested;

	@Column(name = "dairyAllergy")
	private boolean dairyAllergiesInterested;

}
