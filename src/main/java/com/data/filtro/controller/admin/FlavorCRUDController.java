package com.data.filtro.controller.admin;

import com.data.filtro.model.Account;
import com.data.filtro.model.Category;
import com.data.filtro.model.Flavor;
import com.data.filtro.service.FlavorService;
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
@RequestMapping("/admin/flavor")
public class FlavorCRUDController {

    @Autowired
    FlavorService flavorService;

    public Pageable sortFlavor(int currentPage, int pageSize, int sortType) {
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
        Page<Flavor> flavorPage;
        Pageable pageable;
        pageable = sortFlavor(currentPage, pageSize, sortType);
        flavorPage = flavorService.getAllPaging(pageable);
        model.addAttribute("flavors", flavorPage.getContent());
        model.addAttribute("totalPages", flavorPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalElements", flavorPage.getTotalElements());
        model.addAttribute("sortType", sortType);
        return "admin/flavor";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Flavor flavor) {
        flavorService.create(flavor);
        return "redirect:/admin/flavor";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Flavor flavor) {
        flavorService.update(flavor);
        return "redirect:/admin/flavor";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        flavorService.delete(id);
        return "redirect:/admin/flavor";
    }

}
