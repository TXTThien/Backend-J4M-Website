package org.example.service;

import org.example.entity.Discount;

import java.util.List;

public interface IDiscountService {
    List<Discount> getAllDiscounts();
    Discount getDiscountById(Integer id);
    Discount createDiscount(Discount discount);
    Discount updateDiscount(Integer id, Discount discountDetails);
    void deleteDiscount(Integer id);

}
