package com.data.filtro.controller.admin;

import com.data.filtro.model.Account;
import com.data.filtro.model.Category;
import com.data.filtro.model.Flavor;
import com.data.filtro.model.Product;
import com.data.filtro.service.CategoryService;
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
@RequestMapping("/admin/category")
public class CategoryCRUDController {

    @Autowired
    CategoryService categoryService;

    public Pageable sortCategory(int currentPage, int pageSize, int sortType) {
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
        Page<Category> categoryPage;
        Pageable pageable;
        pageable = sortCategory(currentPage, pageSize, sortType);
        categoryPage = categoryService.getAllPaging(pageable);
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalElements", categoryPage.getTotalElements());
        model.addAttribute("sortType", sortType);
        return "admin/category";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Category category) {
        categoryService.create(category);
        return "redirect:/admin/category";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Category category) {
        categoryService.update(category);
        return "redirect:/admin/category";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        categoryService.delete(id);
        return "redirect:/admin/category";
    }
}
