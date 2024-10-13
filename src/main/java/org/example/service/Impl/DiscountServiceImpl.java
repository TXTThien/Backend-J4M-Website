package org.example.service.Impl;

import org.example.entity.Discount;
import org.example.repository.DiscountRepository;
import org.example.service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements IDiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public Discount getDiscountById(Integer id) {
        return discountRepository.findById(id).orElse(null);
    }

    @Override
    public Discount createDiscount(Discount discount) {
        // Kiểm tra nếu CategoryID và ProductTypeID là hợp lệ
        if (discount.getCategoryID() != null) {
            discount.setCategoryID(discount.getCategoryID());
        }
        if (discount.getProductTypeID() != null) {
            discount.setProductTypeID(discount.getProductTypeID());
        }
        return discountRepository.save(discount);
    }


    @Override
    public Discount updateDiscount(Integer id, Discount discountDetails) {
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();

            // Cập nhật categoryID nếu có giá trị
            if (discountDetails.getCategoryID() != null) {
                discount.setCategoryID(discountDetails.getCategoryID());
            }

            // Cập nhật productTypeID nếu có giá trị
            if (discountDetails.getProductTypeID() != null) {
                discount.setProductTypeID(discountDetails.getProductTypeID());
            }

            // Cập nhật các trường khác
            discount.setDiscountPercent(discountDetails.getDiscountPercent());
            discount.setStartDate(discountDetails.getStartDate());
            discount.setEndDate(discountDetails.getEndDate());

            // Cập nhật status nếu có giá trị
            if (discountDetails.getStatus() != null) {
                discount.setStatus(discountDetails.getStatus());
            }

            return discountRepository.save(discount);
        } else {
            return null;
        }
    }


    @Override
    public void deleteDiscount(Integer id) {
        discountRepository.deleteById(id);
    }
}
