package com.aj.diningreview.controller;

import com.aj.diningreview.model.DiningReview;
import com.aj.diningreview.model.Status;
import com.aj.diningreview.repository.DiningReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DiningReviewController {

    private DiningReviewRepository diningReviewRepository;

    public DiningReviewController(DiningReviewRepository diningReviewRepository) {
        this.diningReviewRepository = diningReviewRepository;
    }

    @GetMapping("/admin/reviews")
    public List<DiningReview> reviewsByStatus(@RequestParam  String status) {

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
        //TODO: only if user exists then save it
        return diningReviewRepository.save(diningReview);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<DiningReview> getDiningReviewById(@PathVariable Long id) {

        if (diningReviewRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(diningReviewRepository.findById(id).get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/admin/reviews/{id}")
    public ResponseEntity<DiningReview> approveOrRejectReview(@PathVariable Long id,
                                                              @RequestParam boolean approve) {

        if (diningReviewRepository.findById(id).isPresent()) {
            DiningReview diningReview = diningReviewRepository.findById(id).get();

            if (approve) {
                diningReviewRepository.findById(id)
                        .map(review -> {
                            review.setStatus(Status.APPROVED);
                            return new ResponseEntity<>(diningReviewRepository.save(review), HttpStatus.OK);
                        });
            } else {
                diningReviewRepository.findById(id)
                        .map(review -> {
                            review.setStatus(Status.REJECTED);
                            return new ResponseEntity<>(diningReviewRepository.save(review), HttpStatus.OK);
                        });
            }

            return new ResponseEntity<>(diningReview, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
