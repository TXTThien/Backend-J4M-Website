package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.BillInfo;
import org.example.service.IBillInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entity.enums.Status;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/billinfo")
@RequiredArgsConstructor
public class AdminBillInfoController {

    private final IBillInfoService billInfoService;

    @GetMapping("")
    public ResponseEntity<?> getListBillInfo() {
        List<BillInfo> billInfos = billInfoService.getAllBillInfos();
        return billInfos.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bill information found")
                : ResponseEntity.ok(billInfos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBillInfo(@PathVariable("id") Integer id) {
        Optional<BillInfo> billInfoOptional = billInfoService.getBillInfoById(id);
        if (billInfoOptional.isPresent()) {
            BillInfo billInfo = billInfoOptional.get();
            billInfo.setStatus(Status.Disable); // Chuyển trạng thái thành Disable
            billInfoService.updateBillInfo(billInfo); // Cập nhật BillInfo trong cơ sở dữ liệu
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill info not found");
        }
    }

}
