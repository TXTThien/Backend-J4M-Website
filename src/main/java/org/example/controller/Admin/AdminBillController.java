package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.Bill;
import org.example.service.IBillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/bill")
@RequiredArgsConstructor
public class AdminBillController {

    private final IBillService billService;

    @GetMapping("")
    public ResponseEntity<?> getListBill() {
        List<Bill> bills = billService.getAllBills();
        return bills.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bills found")
                : ResponseEntity.ok(bills);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBillPaidStatus(@PathVariable("id") int id, @RequestBody Map<String, Object> payload) {
        Integer isPaid = (Integer) payload.get("isPaid");
        String status = (String) payload.get("status");

        Bill updatedBill = billService.updateBillPaidStatus(id, isPaid, status);
        if (updatedBill != null) {
            return ResponseEntity.ok(updatedBill);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable("id") Integer id) {
        Optional<Bill> billOptional = billService.getBillById(id);
        if (billOptional.isPresent()) {
            billService.deleteBill(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill not found");
        }
    }
}
//test api
// {
//        "isPaid": 1,
//        "status": "Disable"
//}