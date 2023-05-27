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

@Controller
@RequestMapping("/login")
public class LoginController {
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
    public String show() {
        return "login";
    }

    @PostMapping
    public String login(@RequestParam("accountName") String accountName,
                        @RequestParam("password") String password,
                        @RequestParam("csrfToken") String csrfToken,
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

        String storedCsrfToken = (String) session.getAttribute("csrfToken");
        if (storedCsrfToken == null || !storedCsrfToken.equals(csrfToken)) {
            model.addAttribute("message", "Invalid CsrfToken");
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
            session.removeAttribute("csrfToken");
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
}
