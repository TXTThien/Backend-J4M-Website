package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.BillInfoRepository;
import org.example.repository.BillRepository;
import org.example.repository.CartRepository;
import org.example.service.ICartService;
import org.example.service.IPrebuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrebuyServiceImpl implements IPrebuyService {
    private final   BillRepository billRepository;
    private final BillInfoRepository billInfoRepository;
    private final ICartService cartService;
    private final AccountRepository accountRepository;


    public void createBillInfo(Bill billID, int cartid ) {
        Cart cart = cartService.findCartByCartID(cartid);
        ProductSize productSizeid = cart.getProductSizeID();
        int count = cart.getNumber();
        cartService.hardDeleteCart(cartid);
        BillInfo billInfo = new BillInfo();
        billInfo.setBillID(billID);
        billInfo.setProductSizeID(productSizeid);
        billInfo.setNumber(count);
        billInfo.setStatus(Status.Enable);
        billInfoRepository.save(billInfo);
    }

}
