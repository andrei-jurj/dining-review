package com.aj.diningreview.controller;

import com.aj.diningreview.model.DiningReview;
import com.aj.diningreview.model.User;
import com.aj.diningreview.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	public List<User> all() {
		return userRepository.findAll();
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
			updatedUser.setHasPeanutAllergy(user.getHasPeanutAllergy());
			updatedUser.setHasEggAllergy(user.getHasEggAllergy());
			updatedUser.setHasDairyAllergy(user.getHasDairyAllergy());
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