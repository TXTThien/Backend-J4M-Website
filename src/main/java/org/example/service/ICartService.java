package org.example.service;

import org.example.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    List<Cart> getAllCarts();
    Cart getCartById(Integer id);
    Cart createCart(Cart cart);
    Cart updateCart(Integer id, Cart cartDetails);
    void deleteCart(Integer id);

    List<Cart> findCartsByAccountID(int id);
}