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
import java.util.UUID;

import static com.data.filtro.service.InputService.containsAllowedCharacters;
import static com.data.filtro.service.InputService.isStringLengthLessThan50;

@Controller
@RequestMapping("/register")
public class RegisterController {


    private String csrfToken;

    @Autowired
    UserService userService;

    public String showRegister(Model model) {
        String _csrfToken = generateRandomString();
        csrfToken = _csrfToken;
        System.out.println("csrfToken:" + _csrfToken);
        model.addAttribute("_csrfToken", _csrfToken);
        return "register";
    }

    @PostMapping
    public String registerUser(@RequestParam("userName") String userName,
                               @RequestParam("accountName") String accountName,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("repeatPassword") String repeatPassword,
                               @RequestParam("_csrfParameterName") String csrfTokenForm,
                               HttpSession session,
                               Model model) {

//        String storedCsrfToken = (String) session.getAttribute("csrfToken");
//        if (storedCsrfToken == null || !storedCsrfToken.equals(csrfToken)) {
//            model.addAttribute("errorMessage", "Invalid CsrfToken");
//            return "register";
//        }


        if (!containsAllowedCharacters(userName) || !containsAllowedCharacters(accountName)
                || !containsAllowedCharacters(email) || !isStringLengthLessThan50(userName)
                || !isStringLengthLessThan50(accountName) || !isStringLengthLessThan50(password)) {
            String message = "Tên người dùng, tên tài khoản, email chỉ được chứa các ký tự thường và dấu (), @ và " +
                    "độ dài dưới 50 ký tự";
            model.addAttribute("errorMessage", message);
            return "register";
        }
        System.out.println("Sau khi nhan nut dang ky thi csrf token la: " + csrfToken);
        if (!csrfTokenForm.equals(csrfToken)) {
            String message = "Mã token không đúng";
            model.addAttribute("errorMessage", message);
            return "register";
        }


        try {
            userService.registerUser(userName, accountName, email, password, repeatPassword);
            //session.removeAttribute("csrfToken");
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


    public String generateRandomString() {
        return UUID.randomUUID().toString();
    }

}
