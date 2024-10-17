package org.example.repository;

import org.example.entity.Brand;
import org.example.entity.Product;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findProductByStatusOrderByProductIDDesc(Status status);

    List<Product> findProductsByTitleContaining(String title);

    List<Product> findProductsByBrandIDBrandName(String brand);

    List<Product> findProductsByProductTypeCategoryIDCategoryName(String Category);

    List<Product> findProductsByProductTypeTypeName(String ProductType);

}
