package com.aj.diningreview.controller;

import com.aj.diningreview.model.DiningReview;
import com.aj.diningreview.model.User;
import com.aj.diningreview.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

	private UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = new ArrayList<>();

			userRepository.findAll().forEach(users::add);

			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/users")
	public User newUser(@RequestBody @Validated User newUser) {
		return userRepository.save(newUser);
	}

	public boolean userExists(DiningReview diningReview) {
		return userRepository.findByName(diningReview.getSubmittedBy()) != null;
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {

		Optional<User> userData = userRepository.findById(id);

		if (userData.isPresent()) {
			User updatedUser = userData.get();
			updatedUser.setCity(user.getCity());
			updatedUser.setState(user.getState());
			updatedUser.setZipCode(user.getZipCode());
			updatedUser.setPeanutAllergiesInterested(user.isPeanutAllergiesInterested());
			updatedUser.setEggAllergiesInterested(user.isEggAllergiesInterested());
			updatedUser.setDairyAllergiesInterested(user.isDairyAllergiesInterested());
			return new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/users/{name}")
	public ResponseEntity<User> getUsersByName(@PathVariable String name) {

		if (userRepository.findByName(name) != null) {
			return new ResponseEntity<>(userRepository.findByName(name), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
