package com.data.filtro.controller.admin;

import com.data.filtro.model.Account;
import com.data.filtro.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/account")
public class AccountCRUDController {

    @Autowired
    AccountService accountService;

    public Pageable sortAccount(int currentPage, int pageSize, int sortType) {
        Pageable pageable;
        switch (sortType) {
            case 5, 10, 25, 50 -> pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("accountName"));
            default -> {
                pageSize = 5;
                pageable = PageRequest.of(currentPage - 1, pageSize);
            }
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
        Page<Account> accountPage;
        Pageable pageable;
        pageable = sortAccount(currentPage, pageSize, sortType);
        accountPage = accountService.getAllPaging(pageable);
        model.addAttribute("accounts", accountPage.getContent());
        model.addAttribute("totalPages", accountPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalElements", accountPage.getTotalElements());
        model.addAttribute("sortType", sortType);
        return "admin/account";
    }


    @PostMapping("/create")
    public String create(@ModelAttribute Account account) {
        accountService.create(account);
        return "redirect:/admin/account";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Account account) {
        accountService.update(account);
        return "redirect:/admin/account";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        accountService.delete(id);
        return "redirect:/admin/account";
    }


}
