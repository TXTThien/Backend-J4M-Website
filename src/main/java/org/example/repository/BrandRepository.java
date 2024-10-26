package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand>findAllByStatus(Status status);

}
