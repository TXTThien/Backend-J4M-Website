package org.example.service;

import org.example.entity.Brand;

import java.util.List;
import java.util.Optional;

public interface IBrandService {
    List<Brand> getAllBrands();
    Optional<Brand> getBrandById(Integer id);
    Brand createBrand(Brand brand);
    Brand updateBrand(Integer id, Brand updatedBrand);
    void deleteBrand(Integer id);
    void updateBrandStatus(Brand brand);

}
