package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.repository.ProductTypeRepository;
import org.example.repository.ReviewRepository;
import org.example.service.Impl.ProductTypeServiceImpl;
import org.example.service.Impl.ReviewServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/review")
@RequiredArgsConstructor
public class AdminReviewController {
    private final ReviewRepository reviewRepository;
    private final ReviewServiceImpl reviewService;
}
