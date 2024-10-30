package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.auth.ChangePassword;
import org.example.dto.BuyHistoryDTO;
import org.example.entity.Account;
import org.example.entity.BillInfo;
import org.example.entity.Review;
import org.example.entity.enums.Role;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.service.IAccountService;
import org.example.service.IBillInfoService;
import org.example.service.IReviewService;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.BillInfoServiceImpl;
import org.example.service.securityService.AuthService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {
    private final AccountRepository accountRepository;
    private final IAccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final GetIDAccountFromAuthService getIDAccountService;
    private final IBillInfoService billInfoService;
    private final IReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<Account> getAccountInfo() {
        int idAccount = getIDAccountService.common();
        Account account = accountRepository.findAccountByAccountID(idAccount);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/bought")
    public ResponseEntity<Map<String, Object>> getHistoryBought() {
        int idAccount = getIDAccountService.common();
        List<BillInfo> billInfos = billInfoService.findBillInfoByAccountID(idAccount, Status.Enable);
        List<BuyHistoryDTO> buyHistoryDTOS = new ArrayList<>();

        for (BillInfo billInfo : billInfos) {
            BuyHistoryDTO buyHistoryDTO = new BuyHistoryDTO();

            // Set values in BuyHistoryDTO from BillInfo
            buyHistoryDTO.setDate(billInfo.getBillID().getDate());
            buyHistoryDTO.setNumber(billInfo.getNumber());
            buyHistoryDTO.setProductID(billInfo.getProductSizeID().getProductID().getProductID());
            buyHistoryDTO.setProductTitle(billInfo.getProductSizeID().getProductID().getTitle());
            buyHistoryDTO.setCost(billInfo.getProductSizeID().getProductID().getPrice());

            // Add to the list
            buyHistoryDTOS.add(buyHistoryDTO);
        }

        List<Review> review = reviewService.findReviewByAccountID(idAccount, Status.Enable);

        Map<String, Object> response = new HashMap<>();
        response.put("billInfo", buyHistoryDTOS);
        response.put("review", review);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/updateinfo")
    public ResponseEntity<String> updateAccountByAccountID(
            @RequestBody Account updateAccountRequest) {
        int idAccount = getIDAccountService.common();


        Account currentAccount = accountRepository.findAccountByAccountID(idAccount);

        if (currentAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản không tồn tại.");
        }

        if (updateAccountRequest.getName() != null) {
            currentAccount.setName(updateAccountRequest.getName());
        }
        if (updateAccountRequest.getPhoneNumber() != null) {
            currentAccount.setPhoneNumber(updateAccountRequest.getPhoneNumber());
        }
        if (updateAccountRequest.getAddress() != null) {
            currentAccount.setAddress(updateAccountRequest.getAddress());
        }
        if (updateAccountRequest.getEmail() != null) {
            currentAccount.setEmail(updateAccountRequest.getEmail());
        }

        accountService.updateAccountInfo(currentAccount);

        return ResponseEntity.ok("Cập nhật thông tin tài khoản thành công.");
    }



    @PutMapping("/changepassword")
    public ResponseEntity<String> updateChangePassword(
            @RequestBody ChangePassword changePassword) {

        int idAccount = getIDAccountService.common();
        String newpass = changePassword.getNewpass();
        String curpass = changePassword.getCurpass();

        Account account = accountRepository.findById(idAccount)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        if (!passwordEncoder.matches(curpass, account.getPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu hiện tại không đúng.");
        }

        String updatepass = passwordEncoder.encode(newpass);
        accountRepository.updatePassword(updatepass, idAccount);

        return ResponseEntity.ok("Đổi mật khẩu thành công.");
    }

}
