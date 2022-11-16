package com.aj.diningreview.controller;

import com.aj.diningreview.model.Restaurant;
import com.aj.diningreview.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping("/admin/restaurants")
    public Restaurant newRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {

        if (restaurantRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(restaurantRepository.findById(id).get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getByZipAndAllergyRatingReversed(@RequestParam String zip, @RequestParam String allergy) {
        List<Restaurant> byZip = restaurantRepository.findByZip(zip);

        switch (allergy) {
            case "egg":
                return byZip.stream()
                        .filter(r -> r.getEggAllergyRating() != null)
                        .sorted(Comparator.comparingDouble(Restaurant::getEggAllergyRating).reversed())
                        .collect(Collectors.toList());
            case "peanut":
                return byZip.stream()
                        .filter(r -> r.getPeanutAllergyRating() != null)
                        .sorted(Comparator.comparingDouble(Restaurant::getPeanutAllergyRating).reversed())
                        .collect(Collectors.toList());
            case "dairy":
                return byZip.stream()
                        .filter(r -> r.getDairyAllergyRating() != null)
                        .sorted(Comparator.comparingDouble(Restaurant::getDairyAllergyRating).reversed())
                        .collect(Collectors.toList());
            default:
                return byZip;
        }
    }
}