package com.aj.diningreview.controller;

import com.aj.diningreview.model.DiningReview;
import com.aj.diningreview.model.Restaurant;
import com.aj.diningreview.model.Status;
import com.aj.diningreview.repository.DiningReviewRepository;
import com.aj.diningreview.repository.RestaurantRepository;
import com.aj.diningreview.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DiningReviewController {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private final DiningReviewRepository diningReviewRepository;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    public DiningReviewController(DiningReviewRepository diningReviewRepository, RestaurantRepository restaurantRepository,
                                  UserRepository userRepository) {
        this.diningReviewRepository = diningReviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/reviews")
    public List<DiningReview> reviewsByStatus(@RequestParam String status) {

        switch (status) {
            case "pending":
                return diningReviewRepository.findByStatus(Status.PENDING);
            case "approved":
                return diningReviewRepository.findByStatus(Status.APPROVED);
            case "rejected":
                return diningReviewRepository.findByStatus(Status.REJECTED);
            default:
                return diningReviewRepository.findAll();
        }
    }

    @GetMapping("/admin/reviews/pending")
    public List<DiningReview> getPendingReviews() {
        return diningReviewRepository.findByStatus(Status.PENDING);
    }

    @PostMapping("/reviews")
    public DiningReview newDiningReview(@RequestBody @Validated DiningReview diningReview) {

        boolean restaurantExist = restaurantRepository.existsById(diningReview.getRestaurantId());
        boolean userRegistered = userRepository.findByName(diningReview.getSubmittedBy()) != null;

        //TODO: return proper status code when user is not registered or restaurant id is wrong

        if (restaurantExist && userRegistered) {
            diningReview.setStatus(Status.PENDING);

            return diningReviewRepository.save(diningReview);
        }

        return null;
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<DiningReview> getDiningReviewById(@PathVariable Long id) {

        if (diningReviewRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(diningReviewRepository.findById(id).get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/admin/reviews/{id}")
    public DiningReview approveOrRejectReview(@PathVariable Long id,
                                              @RequestParam boolean isApproved) {

        if (diningReviewRepository.findById(id).isPresent()) {
            DiningReview diningReview = diningReviewRepository.findById(id).get();

            if (isApproved) {
                diningReview.setStatus(Status.APPROVED);
                diningReviewRepository.save(diningReview);

                Long restaurantId = diningReview.getRestaurantId();
                Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

                List<DiningReview> diningReviews = diningReviewRepository.findByStatus(Status.APPROVED);

                double avgDairyScore = avgDairyScore(diningReviews);
                double avgEggScore = avgEggScore(diningReviews);
                double avgPeanutScore = avgPeanutScore(diningReviews);
                double overall = (avgDairyScore + avgEggScore + avgPeanutScore) / 3;

                restaurant.setDairyAllergyRating((int) avgDairyScore);
                restaurant.setEggAllergyRating((int) avgEggScore);
                restaurant.setPeanutAllergyRating((int) avgPeanutScore);
                restaurant.setOverallRating(Double.valueOf(df.format(overall)));

                restaurantRepository.save(restaurant);

            } else {
                diningReviewRepository.findById(id)
                        .map(review -> {
                            review.setStatus(Status.REJECTED);
                            return new ResponseEntity<>(diningReviewRepository.save(review), HttpStatus.OK);
                        });
            }

            return diningReview;
        }

        return null;
    }

    //TODO: get rid of this duplication
    private static double avgPeanutScore(List<DiningReview> byStatus) {
        return byStatus.stream().collect(Collectors.averagingInt(DiningReview::getPeanutScore));
    }

    private static double avgEggScore(List<DiningReview> byStatus) {
        return byStatus.stream().collect(Collectors.averagingInt(DiningReview::getEggScore));
    }
    private static double avgDairyScore(List<DiningReview> byStatus) {
        return byStatus.stream().collect(Collectors.averagingInt(DiningReview::getDairyScore));
    }
}
