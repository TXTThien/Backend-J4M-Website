package org.example.controller.Admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.repository.*;
import org.example.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/product")
@RequiredArgsConstructor
public class AdminProductController {

    private final IProductService productService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OriginRepository originRepository;
    private final BrandRepository brandRepository;
    private final ProductTypeRepository productTypeRepository;


    @GetMapping("")
    public ResponseEntity<?> getListProduct() {
        List<Product> productList = productService.findAll();

        if (!productList.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("products", productList);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Integer productID = product.getProductID();

        if (productID != null && productRepository.findById(productID).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Product with ID " + productID + " already exists.");
        }

        try {
            Product saveProduct = new Product();

            if (product.getProductType() != null) {
                saveProduct.setProductType(product.getProductType());
            }

            if (product.getBrandID() != null) {
                saveProduct.setBrandID(product.getBrandID());
            }

            if (product.getOriginID() != null) {
                saveProduct.setOriginID(product.getOriginID());
            }

            saveProduct.setStatus(product.getStatus());
            saveProduct.setPrice(product.getPrice());
            saveProduct.setMaterial(product.getMaterial());
            saveProduct.setAvatar(product.getAvatar());
            saveProduct.setTitle(product.getTitle());
            saveProduct.setDescription(product.getDescription());

            Product newProduct = productRepository.save(saveProduct);

            return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product.");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @Valid @RequestBody Product newProduct) {
        Product updateProduct = productService.updateProduct(id, newProduct);

        if (updateProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}