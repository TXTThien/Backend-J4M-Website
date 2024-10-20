package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.ProductRepository;
import org.example.repository.ReviewRepository;
import org.example.service.IReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;

    @Override
    public void deleteReview(int id) {
        Review review = reviewRepository.findById(id).orElse(null);
        assert review != null;
        review.setStatus(Status.Disable);
        reviewRepository.save(review);
    }

    @Override
    public Review updateReview(int id, Review review) {
        Review newReview = reviewRepository.findById(id).orElse(null);
        if (newReview!=null){
            newReview.setComment(review.getComment());
            newReview.setRating(review.getRating());
            newReview.setStatus(review.getStatus());
            if (newReview.getProductID()!=null && newReview.getProductID().getProductID()!=null)
            {
                Product product = productRepository.findById(newReview.getProductID().getProductID()).orElse(null);
                newReview.setProductID(product);
            }
            if (newReview.getAccountID()!=null && newReview.getAccountID().getAccountID()!=null)
            {
                Account account = accountRepository.findById(newReview.getAccountID().getAccountID()).orElse(null);
                newReview.setAccountID(account);
            }
            return reviewRepository.save(newReview);
        }
        return null;
    }

    @Override
    public List<Review> findReviewByAccountID(int id, Status status) {
        return reviewRepository.findReviewsByAccountID_AccountIDAndStatus(id,status);
    }

    @Override
    public Review findReviewByAccountIDAndProduct(int id, int product, Status status) {
        return reviewRepository.findReviewsByAccountID_AccountIDAndProductID_ProductIDAndStatus(id, product, status);
    }
}

