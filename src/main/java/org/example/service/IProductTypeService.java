package org.example.service;

import org.example.entity.Category;
import org.example.entity.Product;
import org.example.entity.ProductType;

import java.util.List;

public interface IProductTypeService {
    ProductType updateProductType(int id, ProductType productType);

    void deleteProductType(int id);
    List<ProductType> findAllEnable();


}
