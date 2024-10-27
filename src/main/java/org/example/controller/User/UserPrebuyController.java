package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.dto.CartDTO;
import org.example.entity.Account;
import org.example.entity.Cart;
import org.example.service.IBillInfoService;
import org.example.service.IBillService;
import org.example.service.ICartService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prebuy")
@RequiredArgsConstructor
public class UserPrebuyController {
    private final ICartService cartService;
    private final IBillService billService;
    private final IBillInfoService billInfoService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;

    @GetMapping("")
    public ResponseEntity<List<CartDTO>> getCart() {
        int id = getIDAccountFromAuthService.common();
        List<Cart> cartList = cartService.findCartsByAccountID(id);

        if (cartList != null) {
            List<CartDTO> cartDTOList = cartList.stream().map(cart -> {
                CartDTO cartDTO = new CartDTO();
                cartDTO.setCartID(cart.getCartID());
                cartDTO.setNumber(cart.getNumber());
                cartDTO.setStatus(cart.getStatus());
                cartDTO.setProductID(cart.getProductSizeID().getProductID().getProductID());
                cartDTO.setAvatar(cart.getProductSizeID().getProductID().getAvatar());
                cartDTO.setProductTitle(cart.getProductSizeID().getProductID().getTitle());
                BigDecimal priceWithBonus = cart.getProductSizeID().getProductID().getPrice()
                        .add(cart.getProductSizeID().getSizeID().getBonus());
                cartDTO.setProductPrice(priceWithBonus);

                List<String> sizes = cart.getProductSizeID().getProductID().getProductSizes()
                        .stream()
                        .map(productSize -> productSize.getSizeID().getSizeName())
                        .collect(Collectors.toList());

                cartDTO.setSizes(sizes);
                return cartDTO;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(cartDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
//    @PutMapping("")
//    public ResponseEntity<?> updateCart(){
//
//    }
    }
