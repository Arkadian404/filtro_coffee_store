package com.data.filtro.controller.admin;

import com.data.filtro.model.Account;
import com.data.filtro.model.Category;
import com.data.filtro.model.Order;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.OrderService;
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
@RequestMapping("/admin/order")
public class OrderCRUDController {
    @Autowired
    OrderService orderService;

    public Pageable sortOrder(int currentPage, int pageSize, int sortType) {
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
        Page<Order> orderPage;
        Pageable pageable;
        pageable = sortOrder(currentPage, pageSize, sortType);
        orderPage = orderService.getAllPaging(pageable);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalElements", orderPage.getTotalElements());
        model.addAttribute("sortType", sortType);
        return "admin/order";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Order order) {
        orderService.update(order);
        orderService.updateSoldByOrderStatus(order);
        return "redirect:/admin/order";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        orderService.delete(id);
        return "redirect:/admin/order";
    }


}
