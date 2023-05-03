package com.data.filtro.controller.admin;

import com.data.filtro.model.Account;
import com.data.filtro.model.User;
import com.data.filtro.service.AccountService;
import com.data.filtro.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/user")
public class UserCRUDController {

    @Autowired
    UserService userService;


    @Autowired
    AccountService accountService;

    public Pageable sortUser(int currentPage, int pageSize, int sortType) {
        Pageable pageable;
        switch (sortType) {
            case 5:
            case 10:
            case 25:
            case 50:
                pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
                break;
            default:
                pageSize = 5;
                pageable = PageRequest.of(currentPage - 1, pageSize);
                break;
        }
        return pageable;
    }

    @GetMapping
    public String show(@RequestParam(defaultValue = "5") int sortType, @RequestParam("page") Optional<Integer> page, Model model, HttpSession session) {
        Account admin = (Account) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }
        int currentPage = page.orElse(1);
        int pageSize = sortType;
        List<Account> usableAccounts = accountService.getAppropriateAccountForUser();
        usableAccounts.forEach(st -> System.out.println(usableAccounts.isEmpty() ? "null" : st.getId()));
        System.out.println("HELLO!");
        Pageable pageable;
        Page<User> userPage;
        pageable = sortUser(currentPage, pageSize, sortType);
        userPage = userService.getAllPaging(pageable);
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalElements", userPage.getTotalElements());
        model.addAttribute("usableAccounts", usableAccounts);
        return "admin/user";
    }


    @PostMapping("/create")
    public String create(@ModelAttribute User user) {
        userService.create(user);
        return "redirect:/admin/user";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/admin/user";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        User user = userService.getByUserId(id);
        userService.deleteById(id);
        return "redirect:/admin/user";
    }
}
