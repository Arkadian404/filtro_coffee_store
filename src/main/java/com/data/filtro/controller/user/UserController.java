package com.data.filtro.controller.user;

import com.data.filtro.exception.AuthenticationAccountException;
import com.data.filtro.model.Account;
import com.data.filtro.model.Order;
import com.data.filtro.model.User;
import com.data.filtro.service.AccountService;
import com.data.filtro.service.OrderService;
import com.data.filtro.service.UserService;
import jakarta.servlet.http.HttpSession;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    OrderService orderService;


    @GetMapping
    public String show() {
        String out = "<h1>Kiếm cái gì hả mày?</h1>";
        return out;
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User temp = (User) session.getAttribute("user");
        User user = userService.getByUserId(temp.getId());
        model.addAttribute("user", user);
        return "user/user-profile";
    }

    @PostMapping("/profile/{id}")
    public String processProfile(@PathVariable("id") int id, @ModelAttribute("user") User updatedUser, HttpSession session, Model model) {
        try {
            userService.updateUser(updatedUser);
            session.setAttribute("user", updatedUser);
            model.addAttribute("user", updatedUser);
            model.addAttribute("message", "Cập nhật thông tin thành công!");
        } catch (NotFoundException | ParseException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/user/profile";
    }


    @GetMapping("/billing")
    public String showBilling(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Order> orderList = orderService.getOrderByUserId(user.getId());
        //orderList.forEach(s -> System.out.println(s.getId()));
        model.addAttribute("orderList", orderList);
        return "user/user-billing";
    }

    @GetMapping("/security")
    public String showSecurity() {

        return "user/user-security";
    }


    @PostMapping("/security")
    public String processSecurity(HttpSession session, Model model,
                                  @RequestParam("currentPassword") String currentPassword,
                                  @RequestParam("newPassword") String newPassword,
                                  @RequestParam("repeatNewPassword") String repeatNewPassword) {
        try {
            Account account = (Account) session.getAttribute("account");
            accountService.changePassword(account, currentPassword, newPassword, repeatNewPassword);
            model.addAttribute("message", "Đổi mật khẩu thành công!");
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (AuthenticationAccountException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "user/user-security";
    }

}
