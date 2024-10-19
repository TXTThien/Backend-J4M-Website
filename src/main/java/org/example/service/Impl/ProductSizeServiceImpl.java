package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.ProductRepository;
import org.example.repository.ProductSizeRepository;
import org.example.repository.SizeRepository;
import org.example.service.IProductSizeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSizeServiceImpl implements IProductSizeService {
    private final ProductSizeRepository productSizeRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;

    @Override
    public ProductSize updateProductSize(int id, ProductSize productSize) {
        ProductSize newProductSize = productSizeRepository.findById(id).orElse(null);
        if (newProductSize!=null){
            newProductSize.setStock(productSize.getStock());
            newProductSize.setStatus(productSize.getStatus());
            if (newProductSize.getProductID()!=null && newProductSize.getProductID().getProductID()!=null)
            {
                Product product = productRepository.findById(newProductSize.getProductID().getProductID()).orElse(null);
                newProductSize.setProductID(product);
            }
            if (newProductSize.getSizeID()!=null && newProductSize.getSizeID().getSizeID()!=null)
            {
                Size size = sizeRepository.findById(newProductSize.getSizeID().getSizeID()).orElse(null);
                newProductSize.setSizeID(size);
            }
            return productSizeRepository.save(newProductSize);
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
}