package com.aj.diningreview.repository;

import com.aj.diningreview.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findById(Long id);

    List<Restaurant> findByZip(String zip);
}
