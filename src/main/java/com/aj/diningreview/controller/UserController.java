package com.aj.diningreview.controller;

import com.aj.diningreview.exception.UserNotFoundException;
import com.aj.diningreview.model.User;
import com.aj.diningreview.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/admin/users")
	public ResponseEntity<List<User>> getAllUsers() throws UserNotFoundException {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<User> saveUser(@RequestBody @Validated User user) { //throws UserAlreadyExistsException {
		User savedUser = userService.saveUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	@PutMapping("/user/{name}")
	public ResponseEntity<User> updateUser(@PathVariable("name") String name, @RequestBody User user) {
		return new ResponseEntity<>(userService.updateUser(name, user), HttpStatus.OK);
	}

	@GetMapping("/user/{name}")
	public ResponseEntity<User> getUserByName(@PathVariable String name) throws UserNotFoundException {
		return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
	}
}