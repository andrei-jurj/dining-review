package com.aj.diningreview.service;

import com.aj.diningreview.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    Restaurant saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(Long id);
    List<Restaurant> getRestaurantsByZipAndAllergyRatingDesc(String zip, String allergy);
    List<Restaurant> getAllRestaurants();
}
