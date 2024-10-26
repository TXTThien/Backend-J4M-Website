package org.example.repository;

import org.example.entity.Account;
import org.example.entity.ProductType;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    List<ProductType> findAllByStatus(Status status);
}
