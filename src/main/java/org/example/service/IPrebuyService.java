package org.example.service;

import org.example.entity.Account;
import org.example.entity.Bill;
import org.example.entity.Product;

public interface  IPrebuyService {
    void createBillInfo(Bill billID, int cartid);

}
