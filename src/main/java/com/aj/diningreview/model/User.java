package com.aj.diningreview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Name is mandatory")
	@Column(name = "name", unique = true)
	private String name;

	@NotBlank(message = "Email is mandatory")
	@Column(name = "email", unique = true)
	@Email(regexp=".+@.+\\..+") //TODO:better regexp
	private String email;

	@Column(name = "password")
	@NotNull
	@Length(max = 64)
	private String password;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "zip")
	private String zipCode;

	private Boolean hasPeanutAllergy;

	private Boolean hasEggAllergy;

	private Boolean hasDairyAllergy;

	private Boolean enabled;
}