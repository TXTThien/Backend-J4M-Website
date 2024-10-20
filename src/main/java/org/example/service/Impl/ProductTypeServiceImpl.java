package org.example.service.Impl;

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
        ProductType newProductType = productTypeRepository.findById(id).orElse(null);
        if (newProductType!=null){
            newProductType.setTypeName(productType.getTypeName());
            newProductType.setStatus(productType.getStatus());
            if (newProductType.getCategoryID()!=null && newProductType.getCategoryID().getCategoryID()!=null)
            {
                Category category = categoryRepository.findById(newProductType.getCategoryID().getCategoryID()).orElse(null);
                newProductType.setCategoryID(category);
            }
            return productTypeRepository.save(newProductType);
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
