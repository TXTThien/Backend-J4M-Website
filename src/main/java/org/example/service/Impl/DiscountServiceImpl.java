package org.example.service.Impl;

import org.example.entity.Discount;
import org.example.repository.DiscountRepository;
import org.example.service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.entity.enums.Status;

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
        return discountRepository.save(discount);
    }

    @Override
    public Discount updateDiscount(Integer id, Discount discountDetails) {
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            discount.setDiscountPercent(discountDetails.getDiscountPercent());
            discount.setStartDate(discountDetails.getStartDate());
            discount.setEndDate(discountDetails.getEndDate());
            discount.setStatus(discountDetails.getStatus());
            discount.setCategoryID(discountDetails.getCategoryID());
            discount.setProductTypeID(discountDetails.getProductTypeID());
            return discountRepository.save(discount);
        } else {
            return null; // hoặc ném ngoại lệ nếu muốn xử lý lỗi
        }
    }

    @Override
    public void deleteDiscount(Integer discountId) {
        Optional<Discount> discountOptional = discountRepository.findById(discountId);
        if (discountOptional.isPresent()) {
            Discount discount = discountOptional.get();
            discount.setStatus(Status.Disable); // Chuyển trạng thái thành Disable
            discountRepository.save(discount); // Lưu lại thay đổi vào cơ sở dữ liệu
        }
    }
}
