package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProductDTO;
import org.example.entity.*;
import org.example.entity.enums.Role;
import org.example.entity.enums.Status;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductTypeRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
public class HomeController {
    private final IProductService productService;
    private final IBannerService bannerService;
    private final INewsService newsService;
    private final GetIDAccountFromAuthService getIDAccountService;
    private final IAccountService accountService;
    private final CategoryRepository categoryRepository;
    private final ProductTypeRepository productTypeRepository;
    @RequestMapping("/info")
    public ResponseEntity<?> info(@RequestHeader(value = "Account-ID",required = false) Integer  accountId) {
        if (accountId == null) {
            accountId = -1;
        }
        Account account = accountService.getAccountById(accountId);
        System.out.println("idAccount:" + accountId);
        Map<String, String> response = new HashMap<>();
        if (accountId != -1 && account != null && account.getRole() == Role.user) {
            response.put("redirectUrl", "http://localhost:8000/account");
        } else if (accountId != -1 && account != null && account.getRole() == Role.admin) {
            response.put("redirectUrl", "http://localhost:8000/dashboard");
        } else {
            response.put("redirectUrl", "http://localhost:8000/login");
        }

        return ResponseEntity.ok(response);
    }
    @RequestMapping("/cart")
    public ResponseEntity<?> cart(@RequestHeader(value = "Account-ID",required = false) Integer  accountId) {
        if (accountId == null) {
            accountId = -1;
        }
        Account account = accountService.getAccountById(accountId);
        Map<String, String> response = new HashMap<>();
        if (accountId != -1 && account != null && account.getRole() == Role.user) {
            response.put("redirectUrl", "http://localhost:8000/prebuy");
        } else if (accountId != -1 && account != null && account.getRole() == Role.admin) {
            response.put("redirectUrl", "http://localhost:8000/dashboard");
        } else {
            response.put("redirectUrl", "http://localhost:8000/login");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping({"/", "/home","/j4m"})
    public ResponseEntity<String> homePage() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "http://localhost:8000/")
                .build();
    }
    @GetMapping("/homepage")
    public ResponseEntity<?> getBannerData() {
        Map<String, Object> response = new HashMap<>();
        List<Banner> bannerList = bannerService.find4BannerEnable();
        List<News> newsList = newsService.find4NewsEnable();
        List<ProductDTO> productList = productService.find10HotestProductEnable();
        List<Category> categories = categoryRepository.findAllByStatus(Status.Enable);
        List<ProductType> productTypes = productTypeRepository.findAllByStatus(Status.Enable);
        if (bannerList != null) {
            response.put("bannerList", bannerList);
        }
        if (newsList != null) {
            response.put("newsList", newsList);
        }
        if (productList != null) {
            response.put("productList", productList);
        }
        if (categories != null) {
            response.put("categories", categories);
        }
        if (productTypes != null) {
            response.put("productTypes", productTypes);
        }
        return !response.isEmpty() ?
                ResponseEntity.ok(response) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }
    @RequestMapping(value = "/search",  method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<List<Product>> findProductOrBrand(@RequestParam("searchTerm") String search, Model model) {
        String searchTerm = search.trim();

        List<Product> searchProduct = productService.findByBrand(searchTerm, Status.Enable);
        if (searchProduct.isEmpty())
        {
            searchProduct = productService.findByCategory(searchTerm, Status.Enable);
            if (searchProduct.isEmpty()) {
                searchProduct = productService.findByProductType(searchTerm, Status.Enable);
                if (searchProduct.isEmpty()) {
                    searchProduct = productService.findByTitle(searchTerm, Status.Enable);
                }
            }
        }
        if (searchProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(searchProduct);
    }



}
