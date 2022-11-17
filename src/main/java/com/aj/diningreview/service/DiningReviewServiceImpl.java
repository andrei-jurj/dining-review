package com.aj.diningreview.service;

import com.aj.diningreview.model.DiningReview;
import com.aj.diningreview.model.Restaurant;
import com.aj.diningreview.model.ReviewStatus;
import com.aj.diningreview.repository.DiningReviewRepository;
import com.aj.diningreview.repository.RestaurantRepository;
import com.aj.diningreview.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiningReviewServiceImpl implements DiningReviewService {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private final DiningReviewRepository diningReviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public DiningReviewServiceImpl(DiningReviewRepository diningReviewRepository, RestaurantRepository restaurantRepository,
                                   UserRepository userRepository) {
        this.diningReviewRepository = diningReviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<DiningReview> getReviewsByStatus(String status) {
        switch (status) {
            case "pending":
                return diningReviewRepository.findByReviewStatus(ReviewStatus.PENDING);
            case "approved":
                return diningReviewRepository.findByReviewStatus(ReviewStatus.APPROVED);
            case "rejected":
                return diningReviewRepository.findByReviewStatus(ReviewStatus.REJECTED);
            default:
                return diningReviewRepository.findAll();
        }
    }

    @Override
    public List<DiningReview> findByReviewStatus(ReviewStatus status) {
        return diningReviewRepository.findByReviewStatus(status);
    }

    @Override
    public DiningReview saveDiningReview(DiningReview review) {
        boolean restaurantExist = restaurantRepository.existsById(review.getRestaurantId());
        boolean userRegistered = userRepository.findByName(review.getSubmittedBy()).isPresent();

        //TODO: return proper status code when user is not registered or restaurant id is wrong

        if (restaurantExist && userRegistered) {
            review.setReviewStatus(ReviewStatus.PENDING);

            return diningReviewRepository.save(review);
        }

        return null;
    }

    @Override
    public DiningReview getDiningReviewById(Long id) {
        return diningReviewRepository.findById(id).get();
    }

    @Override
    public DiningReview approveOrRejectDiningReview(Long id, boolean approve) {
        if (diningReviewRepository.findById(id).isPresent()) {
            DiningReview diningReview = diningReviewRepository.findById(id).get();

            if (approve) {
                diningReview.setReviewStatus(ReviewStatus.APPROVED);
                diningReviewRepository.save(diningReview);

                Long restaurantId = diningReview.getRestaurantId();
                Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

                List<DiningReview> diningReviews = diningReviewRepository.findByReviewStatus(ReviewStatus.APPROVED);

                double avgDairyScore = diningReviews.stream().collect(Collectors.averagingInt(DiningReview::getDairyScore));
                double avgEggScore = diningReviews.stream().collect(Collectors.averagingInt(DiningReview::getEggScore));
                double avgPeanutScore = diningReviews.stream().collect(Collectors.averagingInt(DiningReview::getPeanutScore));
                double overall = (avgDairyScore + avgEggScore + avgPeanutScore) / 3;

                restaurant.setDairyAllergyRating(avgDairyScore);
                restaurant.setEggAllergyRating(avgEggScore);
                restaurant.setPeanutAllergyRating(avgPeanutScore);
                restaurant.setOverallRating(Double.valueOf(df.format(overall)));

                restaurantRepository.save(restaurant);

            } else {
                diningReviewRepository.findById(id)
                        .map(review -> {
                            review.setReviewStatus(ReviewStatus.REJECTED);
                            return new ResponseEntity<>(diningReviewRepository.save(review), HttpStatus.OK);
                        });
            }

            return diningReview;
        }

        return null;
    }
}