package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {


    @Autowired
    GetIDAccountFromAuthService getIDAccountService;
    @RequestMapping({"/", "/j4m"})
    public ModelAndView j4m(Model model) {
        int idAccount = getIDAccountService.common();
        System.out.println("IdAccount: "+ idAccount);
        return new ModelAndView("redirect:http://localhost:8000");
    }
}