package org.example.controller.Admin;

import org.example.entity.Image;
import org.example.entity.Product;
import org.example.service.IImageService;
import org.example.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entity.enums.Status;

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
        Product product = productService.getProductById(image.getProduct().getProductID());
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }

        image.setProduct(product);
        Image createdImage = imageService.createImage(image);
        return ResponseEntity.ok(createdImage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Integer id, @RequestBody Image imageDetails) {
        Product product = productService.getProductById(imageDetails.getProduct().getProductID());
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }

        imageDetails.setProduct(product);
        Image updatedImage = imageService.updateImage(id, imageDetails);
        if (updatedImage != null) {
            return ResponseEntity.ok(updatedImage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer id) {
        imageService.disableImage(id); // Gọi phương thức disable thay vì delete
        return ResponseEntity.noContent().build();
    }

}

//testapi
//{
//    "imageURL": "http://examp22232e.com/image1.jpg",
//        "product": {
//    "productID": 3
//},
//    "status": "Disable"
//}

