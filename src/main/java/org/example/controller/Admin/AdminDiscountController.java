package org.example.controller.Admin;

import org.example.entity.Discount;
import org.example.service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entity.enums.Status;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/discount")
public class AdminDiscountController {

    @Autowired
    private IDiscountService discountService;

    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable Integer id) {
        Discount discount = discountService.getDiscountById(id);
        if (discount != null) {
            return ResponseEntity.ok(discount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        Discount createdDiscount = discountService.createDiscount(discount);
        return ResponseEntity.ok(createdDiscount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable Integer id, @RequestBody Discount discountDetails) {
        Discount updatedDiscount = discountService.updateDiscount(id, discountDetails);
        if (updatedDiscount != null) {
            return ResponseEntity.ok(updatedDiscount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
        Discount discount = discountService.getDiscountById(id);
        if (discount == null) {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy discount, trả về lỗi 404
        }

        discount.setStatus(Status.Disable); // Chuyển trạng thái của discount thành Disable
        discountService.updateDiscount(id, discount); // Cập nhật discount trong cơ sở dữ liệu

        return ResponseEntity.noContent().build(); // Trả về phản hồi không nội dung
    }

}
