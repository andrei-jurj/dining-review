package com.aj.diningreview.service;

import com.aj.diningreview.model.DiningReview;
import com.aj.diningreview.model.ReviewStatus;

import java.util.List;

public interface DiningReviewService {

    List<DiningReview> getReviewsByStatus(String status);

    List<DiningReview> findByReviewStatus(ReviewStatus status);

    DiningReview saveDiningReview(DiningReview review);

    DiningReview getDiningReviewById(Long id);

    DiningReview approveOrRejectDiningReview(Long id, boolean accept);
}