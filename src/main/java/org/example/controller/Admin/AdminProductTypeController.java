package org.example.controller.Admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.ProductSize;
import org.example.entity.ProductType;
import org.example.repository.AccountRepository;
import org.example.repository.ProductTypeRepository;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.ProductTypeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/producttype")
@RequiredArgsConstructor
public class AdminProductTypeController {
    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeServiceImpl productTypeService;

    @GetMapping("")
    public ResponseEntity<?> getListProductType(){
        List<ProductType> productTypes = productTypeRepository.findAll();
        if (!productTypes.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("ProductType", productTypes);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No ProductType found");
        }
    }
    @PostMapping("")
    public ResponseEntity<?> createProductType(@RequestBody ProductType productType) {
        Integer productTypeID = productType.getProductTypeID();

        if (productTypeID != null && productTypeRepository.findById(productTypeID).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("ProductType with ID " + productTypeID + " already exists.");
        }
        try {

            ProductType newProductType = productTypeRepository.save(productType);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProductType);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductType> updateProductType(@PathVariable("id") int id, @Valid @RequestBody ProductType productType) {
        ProductType updateProductType = productTypeService.updateProductType(id, productType);

        if (updateProductType == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(updateProductType);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductType(@PathVariable("id") int id) {
        ProductType existingProductSize = productTypeRepository.findById(id).orElse(null);
        if (existingProductSize != null) {
            productTypeService.deleteProductType(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> main
