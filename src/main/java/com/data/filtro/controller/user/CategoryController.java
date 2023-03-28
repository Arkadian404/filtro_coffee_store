package com.data.filtro.controller.user;

import com.data.filtro.model.Category;
import com.data.filtro.model.Flavor;
import com.data.filtro.model.Product;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.FlavorService;
import com.data.filtro.service.ProductService;
import jakarta.persistence.Converter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    FlavorService flavorService;


    public Pageable sortPage(int currentPage, int pageSize, String sortType) {
        Pageable pageable;
        switch (sortType) {
            case "product_name_asc":
                pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("productName").ascending());
                break;
            case "product_name_desc":
                pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("productName").descending());
                break;
            case "price_asc":
                pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("price").ascending());
                break;
            case "price_desc":
                pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("price").descending());
                break;
            case "newest":
                pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("createdDate").ascending());
                break;
            case "oldest":
                pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("createdDate").descending());
                break;
            default:
                pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("sold").descending());
                break;
        }
        return pageable;
    }

    @GetMapping("/{id}")
    public String showProductsByCategory(@PathVariable String id,
                                         @RequestParam(defaultValue = "best_selling") String sortType,
                                         @RequestParam("page") Optional<Integer> page,
                                         Model model) {

        int currentPage = page.orElse(1);
        int pageSize = 6;
        int currentId = 0;
        Pageable pageable;
        Page<Product> productPage;
        Category category = null;

        pageable = sortPage(currentPage, pageSize, sortType);

        if (id.equals("all")) {
            productPage = productService.getAll(pageable);
        } else {
            productPage = productService.getProductByCategory(Integer.parseInt(id), pageable);
            category = categoryService.getCategoryById(Integer.parseInt(id));
            currentId = Integer.parseInt(id);
        }


        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortType", sortType);
        model.addAttribute("category", category);
        model.addAttribute("currentId", currentId);
        System.out.println("sortType: " + sortType);
        return "user/category";
    }


    @GetMapping("/flavors/{id}")
    public String showProductsByFlavor(@PathVariable Integer id,
                                       @RequestParam(defaultValue = "best_selling") String sortType,
                                       @RequestParam(name = "page") Optional<Integer> page,
                                       Model model) {

        int currentPage = page.orElse(1);
        int pageSize = 6;
        int currentFlavorId = id;
        Pageable pageable;
        Page<Product> productPage;
        Flavor flavor = null;

        pageable = sortPage(currentPage, pageSize, sortType);

        productPage = productService.getProductsByFlavor(id, pageable);
        flavor = flavorService.getFlavorById(id);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortType", sortType);
        model.addAttribute("flavor", flavor);
        model.addAttribute("currentFlavorId", currentFlavorId);
        return "user/category";
    }


}
