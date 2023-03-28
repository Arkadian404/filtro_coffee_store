package com.data.filtro.controller.user;

import com.data.filtro.model.Category;
import com.data.filtro.model.Flavor;
import com.data.filtro.model.Product;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.FlavorService;
import com.data.filtro.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sound.sampled.Port;
import java.util.List;

@ControllerAdvice
@RequestMapping({"/", "/product", "/category", "/search"})
public class GlobalController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    FlavorService flavorService;

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        List<Category> categories = categoryService.get5Categories();
        return categories;
    }

    @ModelAttribute("products")
    public List<Product> getProducts() {
        List<Product> productList = productService.getAll();
        return productList;
    }

    @ModelAttribute("flavors")
    public List<Flavor> getFlavors() {
        List<Flavor> flavors = flavorService.getAll();
        return flavors;
    }

}
