package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.BillInfo;
import org.example.entity.enums.Status;
import org.example.repository.BillInfoRepository;
import org.example.service.IBillInfoService;
import org.springframework.stereotype.Service;
import org.example.entity.enums.Status;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillInfoServiceImpl implements IBillInfoService {

    private final BillInfoRepository billInfoRepository;

    @Override
    public List<BillInfo> getAllBillInfos() {
        return billInfoRepository.findAll();
    }

    @Override
    public Optional<BillInfo> getBillInfoById(Integer billInfoId) {
        return billInfoRepository.findById(billInfoId);
    }

    @Override
    public BillInfo updateBillInfo(BillInfo billInfo) {
        return billInfoRepository.save(billInfo);
    }

    @Override
<<<<<<< HEAD
    public void deleteBillInfo(Integer billInfoId) {
        Optional<BillInfo> billInfoOptional = billInfoRepository.findById(billInfoId);
        if (billInfoOptional.isPresent()) {
            BillInfo billInfo = billInfoOptional.get();
            billInfo.setStatus(Status.Disable); // Chuyển trạng thái thành Disable
            billInfoRepository.save(billInfo); // Lưu lại thay đổi vào cơ sở dữ liệu
        }
    }

=======
    public List<BillInfo> findBillInfoByAccountID(int id, Status status) {
        return billInfoRepository.findBillInfoByBillID_AccountID_AccountIDAndStatus(id, status);
    }
>>>>>>> main
}
