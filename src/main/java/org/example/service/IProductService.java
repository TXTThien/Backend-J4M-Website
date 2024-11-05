package org.example.service;

import org.example.dto.ProductDTO;
import org.example.entity.Brand;
import org.example.entity.Product;
import org.example.entity.*;
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


    List<Product> sortProduct (int origin, int size, int category, int productType, int brand);
    List<Product> findByTitle(String title, Status status);

    List<ProductDTO> find10HotestProductEnable();

    List<Product> findProductWithBrand(Integer brandID);

    List<Product> findProductSimilar(Integer productTypeID);

    Integer HowManyBought(int id);
}

