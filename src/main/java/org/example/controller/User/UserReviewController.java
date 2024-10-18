package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.ProductSize;
import org.example.entity.Review;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.ReviewRepository;
import org.example.service.IReviewService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class UserReviewController {
    private final IReviewService reviewService;
    private final AccountRepository accountRepository;
    private final ReviewRepository reviewRepository;

    private final GetIDAccountFromAuthService getIDAccountService;

    @PostMapping("") //Chỉ cần nhập productid, comment, rating
    public ResponseEntity<Review> makeReview(@RequestBody Review review) {
        int idAccount = getIDAccountService.common();
        Account account = accountRepository.findAccountByAccountID(idAccount);
        int idProduct = review.getProductID().getProductID();
        review.setStatus(Status.Enable);
        review.setAccountID(account);
        Review existingReview = reviewService.findReviewByAccountIDAndProduct(idAccount, idProduct, Status.Enable);

        if (existingReview == null) {
            Review newReview = reviewRepository.save(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(newReview);
        } else  {
            existingReview.setRating(review.getRating());
            existingReview.setComment(review.getComment());
            Review updatedReview = reviewRepository.save(existingReview);
            return ResponseEntity.ok(updatedReview);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") int id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
