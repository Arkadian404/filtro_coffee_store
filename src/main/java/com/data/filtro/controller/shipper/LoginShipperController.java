package com.data.filtro.controller.shipper;

import com.data.filtro.exception.AuthenticationAccountException;
import com.data.filtro.model.Account;
import com.data.filtro.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shipper/login")
public class LoginShipperController {
    @Autowired
    AccountService accountService;

    @GetMapping
    public String show() {
        return "shipper/login";
    }

    @PostMapping
    public String login(@RequestParam("accountName") String accountName,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        try {
            Account account = accountService.authenticateShipper(accountName, password);
            session.setAttribute("shipper", account);
            System.out.println(account.getAccountName());
            return "redirect:/shipper";
        } catch (AuthenticationAccountException exception) {
            exception.printStackTrace();
            model.addAttribute("message", exception.getMessage());
        }
        return "shipper/login";
    }
}
