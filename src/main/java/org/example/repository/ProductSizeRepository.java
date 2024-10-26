package org.example.repository;

import org.example.entity.Account;
import org.example.entity.ProductSize;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {
    List<ProductSize> findProductSizesByProductIDProductIDAndStatus(int id, Status status);
}
