package com.data.filtro.controller.admin;

import com.data.filtro.model.Account;
import com.data.filtro.model.Category;
import com.data.filtro.model.Flavor;
import com.data.filtro.model.Product;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.FlavorService;
import com.data.filtro.service.ProductService;
import jakarta.mail.Multipart;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductCURDController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    FlavorService flavorService;


    public Pageable sortProduct(int currentPage, int pageSize, int sortType) {
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

    @GetMapping
    public String show(@RequestParam(defaultValue = "5") int sortType, @RequestParam("page") Optional<Integer> page, Model model, HttpSession session) {
        Account admin = (Account) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }
        int currentPage = page.orElse(1);
        int pageSize = sortType;
        Page<Product> productPage;
        List<Category> categories = categoryService.getAll();
        List<Flavor> flavors = flavorService.getAll();
        Pageable pageable;
        pageable = sortProduct(currentPage, pageSize, sortType);
        productPage = productService.getAll(pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalElements", productPage.getTotalElements());
        model.addAttribute("sortType", sortType);
        model.addAttribute("categories", categories);
        model.addAttribute("flavors", flavors);
        return "admin/product";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file) throws Exception {
        productService.addProduct(product, file);
        return "redirect:/admin/product";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file) throws Exception {
        productService.addProduct(product, file);
        return "redirect:/admin/product";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        productService.deleteById(id);
        return "redirect:/admin/product";
    }


}
