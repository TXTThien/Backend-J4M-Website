package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Bill;
import org.example.repository.BillRepository;
import org.example.service.IBillService;
import org.springframework.stereotype.Service;
import org.example.entity.enums.Status;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements IBillService {

    private final BillRepository billRepository;

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Override
    public Optional<Bill> getBillById(Integer billId) {
        return billRepository.findById(billId);
    }

    @Override
    public Bill updateBillPaidStatus(Integer billId, Boolean isPaid, String status) {
        Optional<Bill> billOptional = billRepository.findById(billId);
        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            bill.setPaid(isPaid != false ? true : false); // Chuyển đổi thành 0 hoặc 1
            bill.setStatus(Status.valueOf(status)); // Cập nhật trạng thái

            return billRepository.save(bill);
        }
        return null;
    }

    @Override
    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public void deleteBill(Integer billId) {
        Optional<Bill> billOptional = billRepository.findById(billId);
        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            bill.setStatus(Status.Disable); // Chuyển trạng thái thành Disable
            billRepository.save(bill); // Lưu lại thay đổi vào cơ sở dữ liệu
        }
    }

}
