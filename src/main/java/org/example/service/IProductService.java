package org.example.service;

<<<<<<< HEAD
import org.example.entity.Brand;
import org.example.entity.Product;
=======
import org.example.entity.*;
>>>>>>> main
import org.example.entity.enums.Status;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    List<Product> findAllEnable();


    Product updateProduct (Integer id, Product newProduct);
    void deleteProduct (Integer id);
    Product getProductById(Integer id);

    List<Product>findByBrand(String brand, Status status);
<<<<<<< HEAD

    List<Product>findByCategory(String category, Status status);
    List<Product>findByProductType(String producttype, Status status);
=======
>>>>>>> main

    List<Product>findByCategory(String category, Status status);
    List<Product>findByProductType(String producttype, Status status);

<<<<<<< HEAD
    List<Product> findByTitle(String title, Status status);
}
=======
    List<Product> sortProduct (int origin, int size, int category, int productType, int brand);
    List<Product> findByTitle(String title, Status status);
}
>>>>>>> main
