package com.aj.diningreview.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

	@NotBlank(message = "City is mandatory")
	@Column(name = "city")
	private String city;

	@NotBlank(message = "State is mandatory")
	@Column(name = "state")
	private String state;

	@NotBlank(message = "Zip is mandatory")
	@Column(name = "zip")
	private String zipCode;

	private Boolean hasPeanutAllergy;

	private Boolean hasEggAllergy;

	private Boolean hasDairyAllergy;

}
