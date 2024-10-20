package org.example.controller.Admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.Origin;
import org.example.entity.ProductSize;
import org.example.repository.AccountRepository;
import org.example.repository.ProductSizeRepository;
import org.example.repository.ProductTypeRepository;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.ProductSizeServiceImpl;
import org.example.service.Impl.ProductTypeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/productsize")
@RequiredArgsConstructor
public class AdminProductSizeController {
    private final ProductSizeRepository productSizeRepository;
    private final ProductSizeServiceImpl productSizeService;


    @GetMapping("")
    public ResponseEntity<?> getListProductSize(){
        List<ProductSize> productSizes = productSizeRepository.findAll();
        if (!productSizes.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("ProductSize", productSizes);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No ProductSize found");
        }
    }
    @PostMapping("")
    public ResponseEntity<?> createProductSize(@RequestBody ProductSize productSize) {
        Integer productSizeID = productSize.getProductSizeID();

        if (productSizeID != null && productSizeRepository.findById(productSizeID).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("ProductSize with ID " + productSizeID + " already exists.");
        }
        try {

            ProductSize newProductSize = productSizeRepository.save(productSize);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProductSize);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductSize> updateProductSize(@PathVariable("id") int id, @Valid @RequestBody ProductSize productSize) {
        ProductSize updateProductSize = productSizeService.updateProductSize(id, productSize);

        if (updateProductSize == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(updateProductSize);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductSize(@PathVariable("id") int id) {
        ProductSize existingProductSize = productSizeRepository.findById(id).orElse(null);
        if (existingProductSize != null) {
            productSizeService.deleteProductSize(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

