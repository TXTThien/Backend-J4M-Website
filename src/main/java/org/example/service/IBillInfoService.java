package org.example.service;

import org.example.entity.BillInfo;

import java.util.List;
import java.util.Optional;

public interface IBillInfoService {
    List<BillInfo> getAllBillInfos();
    Optional<BillInfo> getBillInfoById(Integer billInfoId);
    void deleteBillInfo(Integer billInfoId);

    BillInfo updateBillInfo(BillInfo billInfo);

}
