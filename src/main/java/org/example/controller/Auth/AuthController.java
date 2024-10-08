package org.example.controller.Auth;

import jakarta.servlet.http.HttpSession;
import org.example.auth.AuthenticationService;
import org.example.auth.LoginRequest;
import org.example.auth.RegisterRequest;
import org.example.entity.Account;
import org.example.service.IAccountService;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.EmailServiceImpl;
import org.example.service.securityService.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.example.repository.*;
import org.example.service.securityService.*;

import java.io.Console;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
public class AuthController {

    private final AuthenticationService service;
    private final AccountRepository accountRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;

    @Autowired
    private final EmailServiceImpl emailService;

    @Autowired
    private final IAccountService accountService;

    private final AccountServiceImpl accountServicee;

    @GetMapping("/login")
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "http://localhost:8000/login")
                .build();
    }
    @PostMapping("/success")
    @ResponseBody
    public String success(Model model) {
        Integer idAccount = getIDAccountFromAuthService.common();
        return "idAccount: " + idAccount;
    }

}
