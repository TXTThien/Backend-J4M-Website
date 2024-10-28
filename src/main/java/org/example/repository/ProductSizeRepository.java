package org.example.repository;

import org.example.entity.Account;
import org.example.entity.ProductSize;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {
    List<ProductSize> findProductSizesByProductIDProductIDAndStatus(int id, Status status);
    ProductSize findProductSizeByProductID_ProductIDAndAndSizeID_SizeNameAndStatus(int id, String size, Status status);

    @Transactional
    @Modifying
    @Query("UPDATE ProductSize ps SET ps.stock = ps.stock- :number WHERE ps.productSizeID = :pz")
    void UpdateStock(@Param("pz") int pz, @Param("number") int number);
}
