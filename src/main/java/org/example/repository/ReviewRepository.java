package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findReviewsByAccountID_AccountID(int id);
}
