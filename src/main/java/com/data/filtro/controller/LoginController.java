package com.data.filtro.controller;

import com.data.filtro.Util.RecaptchaVerifier;
import com.data.filtro.exception.AuthenticationAccountException;
import com.data.filtro.model.Account;
import com.data.filtro.model.Cart;
import com.data.filtro.model.GuestCart;
import com.data.filtro.model.User;
import com.data.filtro.service.AccountService;
import com.data.filtro.service.CartService;
import com.data.filtro.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static com.data.filtro.service.InputService.containsAllowedCharacters;

@Controller
@RequestMapping("/login")
public class LoginController {
    private String csrfToken;
    private final AccountService accountService;

    private final CartService cartService;
    private final UserService userService;

    private final RecaptchaVerifier recaptchaVerifier;

    @Autowired
    public LoginController(AccountService accountService, UserService userService, CartService cartService, RecaptchaVerifier recaptchaVerifier) {
        this.accountService = accountService;
        this.userService = userService;
        this.cartService = cartService;
        this.recaptchaVerifier = recaptchaVerifier;
    }

    @GetMapping
    public String show(Model model) {
        String _csrfToken = generateRandomString();
        csrfToken = _csrfToken;
        System.out.println("csrfToken:" + _csrfToken);
        model.addAttribute("_csrfToken", _csrfToken);
        return "login";
    }

    @PostMapping
    public String login(@RequestParam("accountName") String accountName,
                        @RequestParam("password") String password,
                        @RequestParam("_csrfParameterName") String csrfTokenForm,
                        @RequestParam(value = "g-recaptcha-response", required = false) String recaptchaResponse,
                        HttpSession session,
                        Model model) {

        boolean isCaptchaValid = recaptchaVerifier.verify(recaptchaResponse);
        if (recaptchaResponse == null || recaptchaResponse.isEmpty()) {
            model.addAttribute("message", "Please complete the reCAPTCHA verification.");
            System.out.println(model.getAttribute("errorMessage"));
            return "login";
        } else if (!isCaptchaValid) {
            if (!isCaptchaValid) {
                model.addAttribute("message", "InvalidCaptcha");
                return "login";
            }
        }


        if (!containsAllowedCharacters(accountName) || !containsAllowedCharacters(password)) {
            String message = "Tên tài khoản, mật khẩu chỉ được chứa các ký tự thường và dấu (), @";
            model.addAttribute("errorMessage", message);
//            throw new InputNotInvalidException("Tên tài khoản, mật khẩu chỉ được chứa các ký tự thường và dấu (), @");
            return "login";
        }


//        String storedCsrfToken = (String) session.getAttribute("csrfToken");
//        if (storedCsrfToken == null || !storedCsrfToken.equals(csrfToken)) {
//            model.addAttribute("message", "Invalid CsrfToken");
//            return "login";
//        }

        System.out.println("Sau khi nhan nut dang ky thi csrf token la: " + csrfToken);
        if (!csrfTokenForm.equals(csrfToken)) {
            String message = "Mã token không đúng";
            model.addAttribute("errorMessage", message);
            return "login";
        }


        try {
            Account account = accountService.authenticateUser(accountName, password);
            User user = userService.getUserById(account.getUser().getId());
            System.out.println(user.getName());
            session.setAttribute("account", account);
            session.setAttribute("user", user);
            Cart cart = (Cart) session.getAttribute("cart");
            GuestCart guestCart = (GuestCart) session.getAttribute("guestCart");
            if (guestCart != null) {
                cart = cartService.convertGuestCartToCart(guestCart, user);
                session.removeAttribute("guestCart");
            }
            //session.removeAttribute("csrfToken");
            return "redirect:/";
        } catch (AuthenticationAccountException exception) {
            exception.printStackTrace();
            model.addAttribute("message", exception.getMessage());
        }
        return "login";
    }

    @GetMapping("/session")
    public String check(HttpSession session) {
//        Account account = (Account) session.getAttribute("account");
//        System.out.println("session lay duoc la: " + account.getAccountName());
        return "session";
    }


    public String generateRandomString() {
        return UUID.randomUUID().toString();
    }

}
