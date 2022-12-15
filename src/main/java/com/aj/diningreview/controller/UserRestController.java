package com.aj.diningreview.controller;

import com.aj.diningreview.exception.UserNotFoundException;
import com.aj.diningreview.model.User;
import com.aj.diningreview.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/admin/users")
	public ResponseEntity<List<User>> getAllUsers() throws UserNotFoundException {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

	@PostMapping("/users")
	public User postUser(@RequestBody @Validated User user) {
		userService.save(user);
		return user;
	}

	@PutMapping("/users/{name}")
	public ResponseEntity<User> putUser(@PathVariable("name") String name, @RequestBody User user) {
		return (userService.existsByName(name))
				? new ResponseEntity<>(userService.putUser(name, user), HttpStatus.OK)
				: new ResponseEntity<>(postUser(user), HttpStatus.CREATED);
	}

	@GetMapping("/users/{name}")
	public ResponseEntity<User> getUserByName(@PathVariable String name) throws UserNotFoundException {
		return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
	}

	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(@Param("email") String email) {
		return userService.isEmailUnique(email) ? "OK" : "Duplicated";
	}
}