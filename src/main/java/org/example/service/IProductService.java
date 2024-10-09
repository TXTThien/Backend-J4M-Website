package org.example.service;

import org.example.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
}
