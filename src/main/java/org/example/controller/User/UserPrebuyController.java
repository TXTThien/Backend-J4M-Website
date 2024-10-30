package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.dto.CartDTO;
import org.example.dto.CartUpdateRequest;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.BillRepository;
import org.example.repository.DiscountRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prebuy")
@RequiredArgsConstructor
public class UserPrebuyController {
    private final ICartService cartService;
    private final IPrebuyService prebuyService;
    private final DiscountRepository discountRepository;
    private final BillRepository billRepository;

    private final IProductSizeService productSizeService;
    private final IAccountService accountService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;

    @GetMapping("")
    public ResponseEntity<?> getCart() {
        int id = getIDAccountFromAuthService.common();
        List<Cart> cartList = cartService.findCartsByAccountID(id);
        List<Discount> discounts = discountRepository.findAll();
        Map<String, Object> response = new HashMap<>();

        if (cartList != null) {
            List<CartDTO> cartDTOList = cartList.stream().map(cart -> {
                CartDTO cartDTO = new CartDTO();
                cartDTO.setCartID(cart.getCartID());
                cartDTO.setNumber(cart.getNumber());
                cartDTO.setStatus(cart.getStatus());
                cartDTO.setProductID(cart.getProductSizeID().getProductID().getProductID());
                cartDTO.setAvatar(cart.getProductSizeID().getProductID().getAvatar());
                cartDTO.setProductTitle(cart.getProductSizeID().getProductID().getTitle());
                BigDecimal priceWithBonus = cart.getProductSizeID().getProductID().getPrice() ;
                cartDTO.setProductPrice(priceWithBonus);

                List<String> sizes = cart.getProductSizeID().getProductID().getProductSizes()
                        .stream()
                        .map(productSize -> productSize.getSizeID().getSizeName())
                        .collect(Collectors.toList());
                List<ProductSize> productSizes = productSizeService.findProductSizeByProductID(cart.getProductSizeID().getProductID().getProductID());
                List<Integer> stockList = new ArrayList<>();
                for (int i = 0; i < productSizes.size(); i++) {
                    ProductSize productSize = productSizes.get(i);
                    stockList.add(productSize.getStock());
                }
                cartDTO.setSizes(sizes);
                cartDTO.setStock(stockList);

                return cartDTO;
            }).collect(Collectors.toList());
            response.put("cart",cartDTOList);
            response.put("discount", discounts);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable Integer id, @RequestBody CartUpdateRequest request) {

        Cart cart =cartService.getCartById(id);
        ProductSize productSize = productSizeService.findProductSizeByProductIDAndSize(cart.getProductSizeID().getProductID().getProductID(), request.getSize());
        if (productSize == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product size not found");
        }

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        cart.setNumber(request.getNumber());
        cart.setProductSizeID(productSize);

        Cart updatedCart = cartService.updateCart(id, cart);
        return ResponseEntity.ok(updatedCart);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Integer id){
        cartService.hardDeleteCart(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/buy")
    public ResponseEntity<?> buyProduct(@RequestParam("cartID") int[] cartIDs) {
        try {
            int id = getIDAccountFromAuthService.common();
            Account account = accountService.getAccountById(id);

            Bill newBill = new Bill();
            newBill.setAccountID(account);
            newBill.setPaid(false);
            newBill.setStatus(Status.Enable);
            newBill.setDate(LocalDateTime.now());
            billRepository.save(newBill);

            for (int cartID : cartIDs) {
                Cart cart = cartService.findCartByCartID(cartID);
                if (cart == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Cart item with ID " + cartID + " not found.");
                }

                int number = cart.getNumber();
                ProductSize productSize = cart.getProductSizeID();

                prebuyService.createBillInfo(newBill, cartID);
                cartService.deleteCart(cartID);
                productSizeService.updateStock(productSize.getProductSizeID(), number);
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Product purchased successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product: " + e.getMessage());
        }
    }
    public ResponseEntity<?> buyVNPay(int[] cartIDs) {
        try {
            int id = getIDAccountFromAuthService.common();
            Account account = accountService.getAccountById(id);

            Bill newBill = new Bill();
            newBill.setAccountID(account);
            newBill.setPaid(false);
            newBill.setStatus(Status.Enable);
            newBill.setDate(LocalDateTime.now());

            billRepository.save(newBill);

            for (int cartID : cartIDs) {
                Cart cart = cartService.findCartByCartID(cartID);
                if (cart == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Cart item with ID " + cartID + " not found.");
                }

                int number = cart.getNumber();
                ProductSize productSize = cart.getProductSizeID();

                prebuyService.createBillInfo(newBill, cartID);
                cartService.deleteCart(cartID);
                productSizeService.updateStock(productSize.getProductSizeID(), number);
            }
            newBill.setPaid(true);
            billRepository.save(newBill);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Product purchased successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product: " + e.getMessage());
        }
    }


}
