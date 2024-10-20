package org.example.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.entity.News;
import org.example.entity.ProductType;
import org.example.entity.Size;
import org.example.entity.enums.Status;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductTypeRepository;
import org.example.service.IProductTypeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements IProductTypeService {
    private  final ProductTypeRepository productTypeRepository;
    private  final CategoryRepository categoryRepository;

    @Override
    public ProductType updateProductType(int id, ProductType productType) {
        ProductType existingProductType = productTypeRepository.findById(id).orElse(null);
        if (existingProductType != null) {
            existingProductType.setTypeName(productType.getTypeName());
            existingProductType.setStatus(productType.getStatus());

            if (productType.getCategoryID() != null && productType.getCategoryID().getCategoryID() != null) {
                Category category = categoryRepository.findById(productType.getCategoryID().getCategoryID()).orElse(null);
                if (category != null) {
                    existingProductType.setCategoryID(category);
                } else {
                    throw new EntityNotFoundException("Category not found with ID: " + productType.getCategoryID().getCategoryID());
                }
            }
            return productTypeRepository.save(existingProductType);
        }
        return null;
    }


    @Override
    public void deleteProductType(int id) {
        ProductType productType = productTypeRepository.findById(id).orElse(null);
        assert productType != null;
        productType.setStatus(Status.Disable);
        productTypeRepository.save(productType);
    }
}
