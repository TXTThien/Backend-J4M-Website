package org.example.controller.Admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.ProductType;
import org.example.entity.Review;
import org.example.repository.ProductTypeRepository;
import org.example.repository.ReviewRepository;
import org.example.service.Impl.ProductTypeServiceImpl;
import org.example.service.Impl.ReviewServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/review")
@RequiredArgsConstructor
public class AdminReviewController {
    private final ReviewRepository reviewRepository;
    private final ReviewServiceImpl reviewService;

    @GetMapping("")
    public ResponseEntity<?> getListReview(){
        List<Review> reviews = reviewRepository.findAll();
        if (!reviews.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("Review", reviews);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Review found");
        }
    }
    @PostMapping("")
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        Integer reviewID = review.getReviewID();

        if (reviewID != null && reviewRepository.findById(reviewID).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Review with ID " + reviewID + " already exists.");
        }
        try {

            Review newReview = reviewRepository.save(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(newReview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateProductType(@PathVariable("id") int id, @Valid @RequestBody Review review) {
        Review updateProductType = reviewService.updateReview(id, review);

        if (updateProductType == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(updateProductType);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductType(@PathVariable("id") int id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> main
