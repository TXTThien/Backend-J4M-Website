package org.example.service;

import org.example.entity.Bill;

import java.util.List;
import java.util.Optional;

public interface IBillService {
    List<Bill> getAllBills();
    Optional<Bill> getBillById(Integer billId);
    Bill updateBillPaidStatus(Integer billId, Integer isPaid, String status);
    void deleteBill(Integer billId);
    Bill updateBill(Bill bill);


}
