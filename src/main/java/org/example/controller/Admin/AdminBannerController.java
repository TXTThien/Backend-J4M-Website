package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.Banner;
import org.example.repository.ProductRepository;
import org.example.repository.ProductTypeRepository;
import org.example.repository.CategoryRepository;
import org.example.service.IBannerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entity.enums.Status;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/banner")
@RequiredArgsConstructor
public class AdminBannerController {

    private final IBannerService bannerService;
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Banner> getAllBanners() {
        List<Banner> banners = bannerService.findAll();
        if (banners.isEmpty()) {
        }
        return banners;
    }


    @PostMapping
    public ResponseEntity<?> createBanner(@RequestBody Banner banner) {
        if (banner.getProductID() != null && banner.getProductID().getProductID() != null) {
            banner.setProductID(productRepository.findById(banner.getProductID().getProductID()).orElse(null));
        }

        if (banner.getProductTypeID() != null && banner.getProductTypeID().getProductTypeID() != null) {
            banner.setProductTypeID(productTypeRepository.findById(banner.getProductTypeID().getProductTypeID()).orElse(null));
        }

        if (banner.getCategoryID() != null && banner.getCategoryID().getCategoryID() != null) {
            banner.setCategoryID(categoryRepository.findById(banner.getCategoryID().getCategoryID()).orElse(null));
        }
        if (banner.getProductID() == null || banner.getProductTypeID() == null || banner.getCategoryID() == null) {
            return ResponseEntity.badRequest().body("Invalid Product, ProductType, or Category");
        }
        Banner savedBanner = bannerService.save(banner);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBanner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banner> getBannerById(@PathVariable Integer id) {
        Banner banner = bannerService.findById(id);
        if (banner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(banner);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateBanner(@PathVariable Integer id, @RequestBody Banner banner) {
        Banner existingBanner = bannerService.findById(id);
        if (existingBanner == null) {
            return ResponseEntity.notFound().build();
        }

        if (banner.getProductID() != null && banner.getProductID().getProductID() != null) {
            banner.setProductID(productRepository.findById(banner.getProductID().getProductID()).orElse(null));
        }

        if (banner.getProductTypeID() != null && banner.getProductTypeID().getProductTypeID() != null) {
            banner.setProductTypeID(productTypeRepository.findById(banner.getProductTypeID().getProductTypeID()).orElse(null));
        }

        if (banner.getCategoryID() != null && banner.getCategoryID().getCategoryID() != null) {
            banner.setCategoryID(categoryRepository.findById(banner.getCategoryID().getCategoryID()).orElse(null));
        }

        if (banner.getProductID() == null || banner.getProductTypeID() == null || banner.getCategoryID() == null) {
            return ResponseEntity.badRequest().body("Invalid Product, ProductType, or Category");
        }

        existingBanner.setBannerImage(banner.getBannerImage());
        existingBanner.setBannerType(banner.getBannerType());
        existingBanner.setProductID(banner.getProductID());
        existingBanner.setProductTypeID(banner.getProductTypeID());
        existingBanner.setCategoryID(banner.getCategoryID());
        existingBanner.setStatus(banner.getStatus());

        Banner updatedBanner = bannerService.update(existingBanner);
        return ResponseEntity.ok(updatedBanner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        Banner existingBanner = bannerService.findById(id);
        if (existingBanner == null) {
            return ResponseEntity.notFound().build();
        }
        // Thay đổi trạng thái của banner thành Disable
        existingBanner.setStatus(Status.Disable);
        bannerService.update(existingBanner);
        return ResponseEntity.noContent().build();
    }

}
//test api
//{
//        "bannerImage": "http://example222.com/image.jpg",
//        "bannerType": "event",
//        "productID": {
//        "productID": 1
//        },
//        "productTypeID": {
//        "productTypeID": 1
//        },
//        "categoryID": {
//        "categoryID": 1
//        },
//        "status": "Enable"
//}
//
