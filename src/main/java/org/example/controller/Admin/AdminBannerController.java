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
        int count = 0;

        // Đếm số trường không null
        if (banner.getProductID() != null && banner.getProductID().getProductID() != null) {
            banner.setProductID(productRepository.findById(banner.getProductID().getProductID()).orElse(null));
            count++;
        }

        if (banner.getProductTypeID() != null && banner.getProductTypeID().getProductTypeID() != null) {
            banner.setProductTypeID(productTypeRepository.findById(banner.getProductTypeID().getProductTypeID()).orElse(null));
            count++;
        }

        if (banner.getCategoryID() != null && banner.getCategoryID().getCategoryID() != null) {
            banner.setCategoryID(categoryRepository.findById(banner.getCategoryID().getCategoryID()).orElse(null));
            count++;
        }

        // Kiểm tra xem có nhiều hơn một trường được cung cấp không
        if (count != 1) {
            return ResponseEntity.badRequest().body("You must specify exactly one of Product, ProductType, or Category.");
        }

        // Nếu không có vấn đề gì, lưu banner
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
        try {
            Banner existingBanner = bannerService.findById(id);
            if (existingBanner == null) {
                return ResponseEntity.notFound().build();
            }

            int count = 0; // Đếm số trường không null

            if (banner.getProductID() != null && banner.getProductID().getProductID() != null) {
                existingBanner.setProductID(productRepository.findById(banner.getProductID().getProductID()).orElse(null));
                count++;
            }

            if (banner.getProductTypeID() != null && banner.getProductTypeID().getProductTypeID() != null) {
                existingBanner.setProductTypeID(productTypeRepository.findById(banner.getProductTypeID().getProductTypeID()).orElse(null));
                count++;
            }

            if (banner.getCategoryID() != null && banner.getCategoryID().getCategoryID() != null) {
                existingBanner.setCategoryID(categoryRepository.findById(banner.getCategoryID().getCategoryID()).orElse(null));
                count++;
            }

            // Kiểm tra xem có nhiều hơn một trường được cung cấp không
            if (count != 1) {
                return ResponseEntity.badRequest().body("You must specify exactly one of Product, ProductType, or Category.");
            }

            // Cập nhật các trường khác của banner
            existingBanner.setBannerImage(banner.getBannerImage());
            existingBanner.setBannerType(banner.getBannerType());
            existingBanner.setStatus(banner.getStatus());

            Banner updatedBanner = bannerService.update(existingBanner);
            return ResponseEntity.ok(updatedBanner);

        } catch (Exception e) {
            // Log lỗi nếu cần và trả về thông báo lỗi rõ ràng
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the banner.");
        }
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
