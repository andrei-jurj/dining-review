package com.aj.diningreview.controller;

import com.aj.diningreview.model.Restaurant;
import com.aj.diningreview.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RestaurantController {

    RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/admin/restaurants/create")
    public String showForm(Model model) {
        Restaurant restaurant = new Restaurant();
        model.addAttribute("restaurant", restaurant);

        return "create_restaurant_form";
    }

    @PostMapping("/admin/restaurants/create")
    public String submitForm(@Valid @ModelAttribute("restaurant") Restaurant restaurant, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "create_restaurant_form";
        }

        restaurantService.saveRestaurant(restaurant);
        return "create_restaurant_success";
    }

    @GetMapping("/restaurants")
    public String showAll(Model model) {
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        return "all_restaurants";
    }
}