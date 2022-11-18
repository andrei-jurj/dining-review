package com.aj.diningreview.service;

import com.aj.diningreview.model.Restaurant;
import com.aj.diningreview.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).get();
    }

    @Override
    public List<Restaurant> getRestaurantsByZipAndAllergyRatingDesc(String zip, String allergy) {
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