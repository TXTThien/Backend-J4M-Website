package org.example.service.Impl;

import org.example.entity.Banner;
import org.example.repository.BannerRepository;
import org.example.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.entity.enums.Status;


import java.util.List;

@Service
public class BannerServiceImpl implements IBannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Banner save(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public Banner findById(Integer id) {
        return bannerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Banner> findAll() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner update(Banner banner) {
        return bannerRepository.findById(banner.getBannerID())
                .map(existingBanner -> {
                    // Cập nhật các thuộc tính của banner
                    existingBanner.setBannerImage(banner.getBannerImage());
                    existingBanner.setBannerType(banner.getBannerType());
                    existingBanner.setProductID(banner.getProductID());
                    existingBanner.setProductTypeID(banner.getProductTypeID());
                    existingBanner.setCategoryID(banner.getCategoryID());
                    existingBanner.setStatus(banner.getStatus());
                    return bannerRepository.save(existingBanner);
                }).orElse(null);
    }

    @Override
    public boolean delete(Integer id) {
        return bannerRepository.findById(id).map(existingBanner -> {
            existingBanner.setStatus(Status.Disable);
            bannerRepository.save(existingBanner);
            return true;
        }).orElse(false);
    }

}
