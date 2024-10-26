package org.example.service;

import org.example.entity.ProductSize;

import java.util.List;

public interface IProductSizeService {
    ProductSize updateProductSize(int id, ProductSize productSize);

    void deleteProductSize(int id);

    List<ProductSize> findProductSizeByProductID(int id);
}
