package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.BillInfo;
import org.example.entity.enums.Status;
import org.example.repository.BillInfoRepository;
import org.example.service.IBillInfoService;
import org.springframework.stereotype.Service;

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
    public void deleteBillInfo(Integer billInfoId) {
        billInfoRepository.deleteById(billInfoId);
    }

    @Override
    public List<BillInfo> findBillInfoByAccountID(int id, Status status) {
        return billInfoRepository.findBillInfoByBillID_AccountID_AccountIDAndStatus(id, status);
    }
}
