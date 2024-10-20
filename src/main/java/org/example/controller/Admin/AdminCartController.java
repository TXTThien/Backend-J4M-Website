package org.example.controller.Admin;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.Cart;
import org.example.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/cart")
public class AdminCartController {

    private final ICartService cartService;

    @Autowired
    public AdminCartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody Cart cart) {
        try {
            // Kiểm tra các thuộc tính null
            if (cart.getProductSizeID() == null) {
                return ResponseEntity.badRequest().body("ProductSizeID cannot be null.");
            }
            if (cart.getAccountID() == null) {
                return ResponseEntity.badRequest().body("AccountID cannot be null.");
            }

            // Tạo Cart
            Cart createdCart = cartService.createCart(cart);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);

        } catch (Exception e) {
            // Log lỗi nếu cần và trả về thông báo lỗi rõ ràng
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the cart.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Integer id) {
        Cart cart = cartService.getCartById(id);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        if (cart.getProductSizeID() == null) {
            return ResponseEntity.badRequest().body(cart);  // Trả về cart nếu thiếu productSizeID
        }
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        if (carts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Kiểm tra nếu bất kỳ cart nào không có ProductSizeID
        for (Cart cart : carts) {
            if (cart.getProductSizeID() == null) {
                return ResponseEntity.badRequest().body(carts); // Trả về lỗi nếu ProductSizeID không có
            }
        }
        return ResponseEntity.ok(carts);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Integer id, @RequestBody Cart cart) {
        if (cart.getProductSizeID() == null) {
            return ResponseEntity.badRequest().build(); // Kiểm tra xem productSizeID có null không
        }
        Cart updatedCart = cartService.updateCart(id, cart);
        if (updatedCart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCart);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}

//test api
//        {
//                "number": 5,
//                "productSizeID": {
//                "productSizeID": 3
//                },
//                "accountID": {
//                "accountID": 2
//                },
//                "status": "Enable"
//        }