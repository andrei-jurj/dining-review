package com.aj.diningreview.repository;

import com.aj.diningreview.model.DiningReview;
import com.aj.diningreview.model.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiningReviewRepository extends JpaRepository<DiningReview, Long> {

    List<DiningReview> findByReviewStatus(ReviewStatus reviewStatus);
}
