package org.example.service;

import org.example.entity.Brand;
import org.example.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    List<Product> findAllEnable();


    Product updateProduct (Integer id, Product newProduct);
    void deleteProduct (Integer id);
    Product getProductById(Integer id);

    List<Product>findByBrand(String brand);

    List<Product>findByCategory(String category);
    List<Product>findByProductType(String producttype);


    List<Product> findByTitle(String title);
}
