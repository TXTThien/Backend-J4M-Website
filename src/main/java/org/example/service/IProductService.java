package org.example.service;

import org.example.entity.Brand;
import org.example.entity.Product;
import org.example.entity.enums.Status;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    List<Product> findAllEnable();


    Product updateProduct (Integer id, Product newProduct);
    void deleteProduct (Integer id);
    Product getProductById(Integer id);

    List<Product>findByBrand(String brand, Status status);

    List<Product>findByCategory(String category, Status status);
    List<Product>findByProductType(String producttype, Status status);


    List<Product> findByTitle(String title, Status status);
}