package org.example.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.ProductRepository;
import org.example.repository.ProductSizeRepository;
import org.example.repository.SizeRepository;
import org.example.service.IProductSizeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSizeServiceImpl implements IProductSizeService {
    private final ProductSizeRepository productSizeRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;

    @Override
    public ProductSize updateProductSize(int id, ProductSize productSize) {
        ProductSize existingProductSize = productSizeRepository.findById(id).orElse(null);
        if (existingProductSize != null) {
            existingProductSize.setStock(productSize.getStock());
            existingProductSize.setStatus(productSize.getStatus());

            if (productSize.getProductID() != null && productSize.getProductID().getProductID() != null) {
                Product product = productRepository.findById(productSize.getProductID().getProductID()).orElse(null);
                if (product != null) {
                    existingProductSize.setProductID(product);
                } else {
                    throw new EntityNotFoundException("Product not found with ID: " + productSize.getProductID().getProductID());
                }
            }

            if (productSize.getSizeID() != null && productSize.getSizeID().getSizeID() != null) {
                Size size = sizeRepository.findById(productSize.getSizeID().getSizeID()).orElse(null);
                if (size != null) {
                    existingProductSize.setSizeID(size);
                } else {
                    throw new EntityNotFoundException("Size not found with ID: " + productSize.getSizeID().getSizeID());
                }
            }

            return productSizeRepository.save(existingProductSize);
        }
        return null;
    }


    @Override
    public void deleteProductSize(int id) {
        ProductSize productSize = productSizeRepository.findById(id).orElse(null);
        assert productSize != null;
        productSize.setStatus(Status.Disable);
        productSizeRepository.save(productSize);
    }

    @Override
    public List<ProductSize> findProductSizeByProductID(int id) {
        return productSizeRepository.findProductSizesByProductIDProductIDAndStatus(id,Status.Enable);
    }
}
