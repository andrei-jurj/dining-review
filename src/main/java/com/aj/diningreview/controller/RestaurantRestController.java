package com.aj.diningreview.controller;

import com.aj.diningreview.model.Restaurant;
import com.aj.diningreview.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantRestController {

    private final RestaurantService restaurantService;

    public RestaurantRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/admin/restaurants")
    public Restaurant newRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.saveRestaurant(restaurant);
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantService.getRestaurantById(id), HttpStatus.OK);
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurantsByZipAndAllergyRatingDesc(@RequestParam String zip, @RequestParam String allergy) {
        return restaurantService.getRestaurantsByZipAndAllergyRatingDesc(zip, allergy);
    }
}