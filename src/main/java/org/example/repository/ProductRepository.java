package org.example.repository;

import org.example.dto.ProductDTO;
import org.example.entity.Brand;
import org.example.entity.Product;
import org.example.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findProductByStatusOrderByProductIDDesc(Status status);

    List<Product> findProductsByTitleContainingAndStatusOrderByProductIDDesc(String title , Status status);

    List<Product> findProductsByBrandIDBrandNameAndStatusOrderByProductIDDesc(String brand, Status status);

    List<Product> findProductsByProductTypeCategoryIDCategoryNameAndStatusOrderByProductIDDesc(String Category , Status status);

    List<Product> findProductsByProductTypeTypeNameAndStatusOrderByProductIDDesc(String ProductType , Status status);
    List<Product> findProductsByBrandIDBrandIDAndStatusOrderByProductIDDesc(int id,Pageable pageable,Status status);
    List<Product>findProductsByProductType_ProductTypeIDAndStatusOrderByProductIDDesc(int id, Pageable pageable, Status status);
    @Query("SELECT new org.example.dto.ProductDTO(p.productID, p.avatar, p.title, SUM(bi.number), p.material, p.price) " +
            "FROM Product p " +
            "JOIN ProductSize ps ON ps.productID = p " +
            "JOIN Billinfo bi ON bi.productSizeID = ps " +
            "where bi.status = 'Enable' GROUP BY p.productID " +
            "ORDER BY SUM(bi.number) DESC")
    List<ProductDTO> findTop10BestSellingProducts(Pageable pageable);

    @Query("SELECT COALESCE(SUM(bi.number), 0) " +
            "FROM Product p " +
            "JOIN ProductSize ps ON ps.productID = p " +
            "JOIN Billinfo bi ON bi.productSizeID = ps " +
            "WHERE bi.status = 'Enable' AND p.productID = :id")
    int HowManyBought(@Param("id") int id);

    @Query("SELECT p FROM ProductSize ps " +
            "JOIN Product p ON ps.productID = p " +
            "JOIN Size s ON ps.sizeID = s " +
            "JOIN Brand b ON p.brandID = b " +
            "JOIN Origin o ON p.originID = o " +
            "JOIN ProductType pt ON p.productType = pt " +
            "JOIN Category c ON pt.categoryID = c " +
            "WHERE (COALESCE(:originid, 0) = 0 OR o.originID = :originid) " +
            "AND (COALESCE(:sizeid, 0) = 0 OR s.sizeID = :sizeid) " +
            "AND (COALESCE(:categoryid, 0) = 0 OR c.categoryID = :categoryid) " +
            "AND (COALESCE(:producttypeid, 0) = 0 OR pt.productTypeID = :producttypeid) " +
            "AND (COALESCE(:brandid, 0) = 0 OR b.brandID = :brandid)"+
            "AND p.status = 'Enable' ORDER BY p.productID DESC ")
    List<Product> sortProduct(@Param("originid") Integer originid,
                              @Param("sizeid") Integer sizeid,
                              @Param("categoryid") Integer categoryid,
                              @Param("producttypeid") Integer producttypeid,
                              @Param("brandid") Integer brandid);
}

