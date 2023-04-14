package com.data.filtro.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/profile")
    public String showProfile() {

        return "user/user-profile";
    }

    @GetMapping("/billing")
    public String showBilling() {

        return "user/user-billing";
    }

    @GetMapping("/security")
    public String showSecurity() {

        return "user/user-security";
    }

}
