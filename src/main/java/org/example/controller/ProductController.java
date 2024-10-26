package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.service.*;
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
    private final ICategoryService categoryService;
    private final IProductTypeService productTypeService;
    private final IBrandService brandService;
    private final IOriginService originService;
    private final ISizeService sizeService;

    @GetMapping("")
    public ResponseEntity<?> getListProduct() {
        List<Product> productList = productService.findAllEnable();
        List<Category> categoryList = categoryService.findAllEnable();
        List<ProductType> productTypeList = productTypeService.findAllEnable();
        List<Brand> brandList = brandService.findAllEnable();
        List<Origin> originList = originService.findAllEnable();
        List<Size> sizeList = sizeService.findAllEnable();
        Map<String, Object> response = new HashMap<>();

        if (!productList.isEmpty()) {
            response.put("products", productList);
        }
        if (!categoryList.isEmpty()) {
            response.put("category", categoryList);
        }
        if (!originList.isEmpty()) {
            response.put("origin", originList);
        }
        if (!brandList.isEmpty()) {
            response.put("brand", brandList);
        }
        if (!productTypeList.isEmpty()) {
            response.put("productType", productTypeList);
        }
        if (!productTypeList.isEmpty()) {
            response.put("size", sizeList);
        }
        if (!response.isEmpty()){
            return ResponseEntity.ok(response);
        }
        else {
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
