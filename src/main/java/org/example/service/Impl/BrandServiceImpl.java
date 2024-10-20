package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Brand;
import org.example.entity.enums.Status;
import org.example.repository.BrandRepository;
import org.example.service.IBrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements IBrandService {
    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> getBrandById(Integer id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand createBrand(Brand brand) {
        brand.setStatus(Status.Enable);
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Integer id, Brand updatedBrand) {
        return brandRepository.findById(id).map(existingBrand -> {
            existingBrand.setBrandName(updatedBrand.getBrandName());
            existingBrand.setStatus(updatedBrand.getStatus());
            return brandRepository.save(existingBrand);
        }).orElse(null);
    }

    @Override
    public void updateBrandStatus(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Integer id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            brand.setStatus(Status.Disable); // Cập nhật trạng thái thành DISABLE
            brandRepository.save(brand); // Lưu lại thay đổi
        }
    }



}
