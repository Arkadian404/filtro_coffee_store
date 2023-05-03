package com.data.filtro.controller.admin;

import com.data.filtro.model.Account;
import com.data.filtro.model.Staff;
import com.data.filtro.service.AccountService;
import com.data.filtro.service.StaffService;
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
@RequestMapping("/admin/staff")
public class StaffCRUDController {

    @Autowired
    StaffService staffService;

    @Autowired
    AccountService accountService;

    public Pageable sortStaff(int currentPage, int pageSize, int sortType) {
        Pageable pageable;
        switch (sortType) {
            case 5, 10, 25, 50 -> pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
            default -> {
                pageSize = 5;
                pageable = PageRequest.of(currentPage - 1, pageSize);
            }
        }
        return pageable;
    }

    @GetMapping()
    public String show(@RequestParam(defaultValue = "5") int sortType, @RequestParam("page") Optional<Integer> page, Model model, HttpSession session) {
        Account admin = (Account) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }
        int currentPage = page.orElse(1);
        int pageSize = sortType;
        Page<Staff> staffPage;
        List<Account> eligibleStaffs = accountService.getEligibleAccountForStaff();
        Pageable pageable;
        pageable = sortStaff(currentPage, pageSize, sortType);
        staffPage = staffService.getAllPaging(pageable);
        model.addAttribute("staffs", staffPage.getContent());
        model.addAttribute("totalPages", staffPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalElements", staffPage.getTotalElements());
        model.addAttribute("sortType", sortType);
        model.addAttribute("eligibleStaffs", eligibleStaffs);
        return "admin/staff";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Staff staff) {
        staffService.create(staff);
        return "redirect:/admin/staff";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Staff staff) {
        staffService.update(staff);
        return "redirect:/admin/staff";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        staffService.delete(id);
        return "redirect:/admin/staff";
    }

}
