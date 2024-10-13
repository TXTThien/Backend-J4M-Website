package org.example.service;

import org.example.entity.Review;

public interface IReviewService {
    void deleteReview(int id);

    Review updateReview(int id, Review review);
}
