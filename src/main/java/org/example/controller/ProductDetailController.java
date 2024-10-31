package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class ProductDetailController {
    private final IProductService productService;
    private final IReviewService reviewService;
    private final IProductSizeService productSizeService;
    private final IImageService iImageService;

    private final ICartService cartService;
    private final IAccountService accountService;

    private final GetIDAccountFromAuthService getIDAccountService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> ProductDetail(@PathVariable("id") int id){
        Product product = productService.getProductById(id);
        List<Review> reviewList = reviewService.findReviewByProductID (id);
        List<ProductSize> productSizesList = productSizeService.findProductSizeByProductID(id);
        List<Image> imageList = iImageService.findImagesByProductID(id);
        Map<String, Object> response = new HashMap<>();

        if (product != null && product.getStatus() == Status.Enable) {
            response.put("product", product);
            response.put("reviews", reviewList);
            response.put("productSizes", productSizesList);
            response.put("imageList", imageList);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Product not available or disabled.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PostMapping("/addToPrebuy")
    public  ResponseEntity<?> AddToCart (@RequestBody Cart cart){
        int idAccount = getIDAccountService.common();
        Account account = accountService.getAccountById(idAccount);
        try {
            if (cart.getProductSizeID() == null) {
                return ResponseEntity.badRequest().body("ProductSizeID cannot be null.");
            }
            if (cart.getAccountID() == null) {
                cart.setAccountID(account);
            }
            if (cart.getStatus() == null) {
                cart.setStatus(Status.Enable);
            }
            Cart createdCart = cartService.createCart(cart);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the cart.");
        }
    }

}
