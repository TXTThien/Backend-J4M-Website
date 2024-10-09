package org.example.service.Impl;

import jakarta.transaction.Transactional;
import org.example.auth.RegisterRequest;
import org.example.entity.*;
import org.example.entity.enums.Role;

import lombok.RequiredArgsConstructor;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.service.IAccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Account> getAllAccounts() {
        return null;
    }


    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public Account getAccountById(Integer accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        return optionalAccount.orElse(null);
    }

    @Override
    public Account createAccount(Account account) {
        // Add any validation or business logic before saving
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Integer accountId, Account updatedAccount) {
        // Add logic to update account
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account existingAccount = optionalAccount.get();
            // Update properties of existingAccount with those of updatedAccount
            // Save the updated account
            return accountRepository.save(existingAccount);
        } else {
            return null; // Account not found
        }
    }

    @Override
    public void deleteAccount(Integer accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public Account makeAccount(RegisterRequest request) {
        return Account.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.user)
                .status(Status.Enable)
                .build();
    }

    @Override
    public void saveAccount(RegisterRequest request) {
        // You can implement the save logic if needed
    }

    @Override
    public Account findAccountByAccountID(int accountid) {
        return accountRepository.findAccountByAccountID(accountid);
    }

    @Override
    @Transactional
    public void updateAddressEmailPhoneNumberAccount(String name, String phoneNumber, String address, int accountID) {
        accountRepository.updateAddressEmailPhoneNumberAccount(name,phoneNumber,address,accountID);
    }

    @Override
    public void updatePassword(String password, int accountID) {
        accountRepository.updatePassword(password,accountID);
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    @Override
    public Optional<Account> findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }
    @Override
    public <S extends Account> S save(S entity) {
        return accountRepository.save(entity);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    @Override
    public String findEmailByUserName(String username) {
        return accountRepository.findEmailByUsername(username);
    }

}