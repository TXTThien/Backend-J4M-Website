package org.example.service;

import org.example.entity.ProductType;

public interface IProductTypeService {
    ProductType updateProductType(int id, ProductType productType);

    void deleteProductType(int id);

}
