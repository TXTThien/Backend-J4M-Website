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
    public Bill updateBillPaidStatus(Integer billId, Integer isPaid, String status) {
        Optional<Bill> billOptional = billRepository.findById(billId);
        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            bill.setPaid(isPaid != 0 ? 1 : 0); // Chuyển đổi thành 0 hoặc 1
            bill.setStatus(Status.valueOf(status)); // Cập nhật trạng thái

            return billRepository.save(bill);
        }
        return null;
    }

    @Override
    public void deleteBill(Integer billId) {
        billRepository.deleteById(billId);
    }
}
