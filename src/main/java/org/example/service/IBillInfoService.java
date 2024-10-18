package org.example.service;

import org.example.entity.BillInfo;
import org.example.entity.enums.Status;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

public interface IBillInfoService {
    List<BillInfo> getAllBillInfos();
    Optional<BillInfo> getBillInfoById(Integer billInfoId);
    void deleteBillInfo(Integer billInfoId);

    List<BillInfo>findBillInfoByAccountID (int id, Status status);
}
