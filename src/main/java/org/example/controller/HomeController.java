package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Brand;
import org.example.entity.Product;
import org.example.service.Impl.BrandServiceImpl;
import org.example.service.Impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
public class HomeController {
    private final ProductServiceImpl productService;

    @GetMapping({"/", "/home","/j4m"})
    public ResponseEntity<String> homePage() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "http://localhost:8000/") // Chuyển hướng tới /
                .build();
    }
    @RequestMapping(value = "/search",  method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<List<Product>> findProductOrBrand(@RequestParam("searchTerm") String searchTerm, Model model) {
        List<Product> searchProduct = productService.findByBrand(searchTerm);
        if (searchProduct.isEmpty())
        {
            searchProduct = productService.findByCategory(searchTerm);
            if (searchProduct.isEmpty()) {
                searchProduct = productService.findByProductType(searchTerm);
                if (searchProduct.isEmpty()) {
                    searchProduct = productService.findByTitle(searchTerm);
                }
            }
        }
        if (searchProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(searchProduct);
    }



}
