package com.data.filtro.controller.admin;

import com.data.filtro.exception.AuthenticationAccountException;
import com.data.filtro.model.Account;
import com.data.filtro.model.Cart;
import com.data.filtro.model.GuestCart;
import com.data.filtro.model.User;
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
@RequestMapping("/admin/login")
public class LoginAdminController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public String show() {
        return "admin/login";
    }

    @PostMapping
    public String login(@RequestParam("accountName") String accountName,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        try {
            Account account = accountService.authenticateAdmin(accountName, password);
            session.setAttribute("admin", account);
            System.out.println(account.getAccountName());
            return "redirect:/admin";
        } catch (AuthenticationAccountException exception) {
            exception.printStackTrace();
            model.addAttribute("message", exception.getMessage());
        }
        return "admin/login";
    }

}
