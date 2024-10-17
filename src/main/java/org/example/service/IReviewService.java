package org.example.service;

import org.example.entity.Review;

import java.util.List;

public interface IReviewService {
    void deleteReview(int id);

    Review updateReview(int id, Review review);
    List<Review> findReviewByAccountID(int id);
}
