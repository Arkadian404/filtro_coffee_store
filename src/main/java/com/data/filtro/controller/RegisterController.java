package com.data.filtro.controller;

import com.data.filtro.exception.AccountNameExistException;
import com.data.filtro.exception.PasswordDoNotMatchException;
import com.data.filtro.exception.PasswordRuleException;
import com.data.filtro.repository.UserRepository;
import com.data.filtro.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping
    public String showRegister() {
        return "register";
    }

    @PostMapping
    public String registerUser(@RequestParam("userName") String userName,
                               @RequestParam("accountName") String accountName,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("repeatPassword") String repeatPassword,
                               @RequestParam("csrfToken") String csrfToken,
                               HttpSession session,
                               Model model) {

        String storedCsrfToken = (String) session.getAttribute("csrfToken");
        if (storedCsrfToken == null || !storedCsrfToken.equals(csrfToken)) {
            model.addAttribute("errorMessage", "Invalid CsrfToken");
            return "register";
        }

        try {
            userService.registerUser(userName, accountName, email, password, repeatPassword);
            session.removeAttribute("csrfToken");
        } catch (AccountNameExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "register";
        } catch (PasswordRuleException ex) {
            model.addAttribute("errorMessages", ex.getErrorMessages());
            return "register";
        } catch (PasswordDoNotMatchException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "register";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String message = "Đăng ký thành công! Đăng nhập ngay!";
        model.addAttribute("message", message);
        return "register";
    }


}
