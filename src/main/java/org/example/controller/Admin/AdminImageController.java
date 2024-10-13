package org.example.controller.Admin;

import org.example.entity.Image;
import org.example.entity.Product; // Import Product entity
import org.example.service.IImageService;
import org.example.service.IProductService; // Import product service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/images")
public class AdminImageController {

    @Autowired
    private IImageService imageService;

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable Integer id) {
        Image image = imageService.getImageById(id);
        if (image != null) {
            return ResponseEntity.ok(image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        // Lấy Product từ ProductID trong image
        Product product = productService.getProductById(image.getProduct().getProductID());
        if (product == null) {
            return ResponseEntity.badRequest().build(); // Trả về lỗi nếu không tìm thấy product
        }

        image.setProduct(product); // Thiết lập Product cho Image
        Image createdImage = imageService.createImage(image);
        return ResponseEntity.ok(createdImage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Integer id, @RequestBody Image imageDetails) {
        // Tương tự như trên, tìm Product theo ProductID
        Product product = productService.getProductById(imageDetails.getProduct().getProductID());
        if (product == null) {
            return ResponseEntity.badRequest().build(); // Trả về lỗi nếu không tìm thấy product
        }

        imageDetails.setProduct(product); // Thiết lập Product cho Image
        Image updatedImage = imageService.updateImage(id, imageDetails);
        if (updatedImage != null) {
            return ResponseEntity.ok(updatedImage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
