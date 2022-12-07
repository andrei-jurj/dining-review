package com.aj.diningreview.controller;

import com.aj.diningreview.model.DiningReview;
import com.aj.diningreview.model.ReviewStatus;
import com.aj.diningreview.service.DiningReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DiningReviewController {

    private final DiningReviewService diningReviewService;

    public DiningReviewController(DiningReviewService diningReviewService) {
        this.diningReviewService = diningReviewService;
    }

    @GetMapping("/admin/reviews")
    public List<DiningReview> reviewsByStatus(@RequestParam String status) {
        return diningReviewService.getReviewsByStatus(status);
    }

    @GetMapping("/admin/reviews/pending")
    public List<DiningReview> getPendingReviews() {
        return diningReviewService.findByReviewStatus(ReviewStatus.PENDING);
    }

    @PostMapping("/reviews")
    public DiningReview saveDiningReview(@RequestBody @Validated DiningReview diningReview) {
        return diningReviewService.saveDiningReview(diningReview);
    }

    @GetMapping("/admin/reviews/{id}")
    public ResponseEntity<DiningReview> getDiningReviewById(@PathVariable Long id) {
        return new ResponseEntity<>(diningReviewService.getDiningReviewById(id), HttpStatus.OK);
    }

    @PutMapping("/admin/reviews/{id}")
    public DiningReview approveOrRejectReview(@PathVariable Long id,
                                              @RequestParam boolean accept) {

        return diningReviewService.approveOrRejectDiningReview(id, accept);
    }
}