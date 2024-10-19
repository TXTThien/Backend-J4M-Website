package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getListProduct() {
        List<Product> productList = productService.findAllEnable();

        if (!productList.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("products", productList);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found");
        }
    }
    @RequestMapping("/sort")
    public ResponseEntity<?> sortListProduct(
            @RequestParam(value = "origin", required = false) Integer origin,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "category", required = false) Integer category,
            @RequestParam(value = "productType", required = false) Integer productType,
            @RequestParam(value = "brand", required = false) Integer brand) {

        try {
            List<Product> productList = productService.sortProduct(
                    origin != null ? origin : 0,
                    size != null ? size : 0,
                    category != null ? category : 0,
                    productType != null ? productType : 0,
                    brand != null ? brand : 0
            );

            if (productList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while sorting the products: " + e.getMessage());
        }
    }

}
