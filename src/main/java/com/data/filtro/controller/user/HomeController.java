package com.data.filtro.controller.user;

import com.data.filtro.model.Category;
import com.data.filtro.model.Product;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    ProductService productService;


    @GetMapping
    public String home(Model model) {
        List<Product> productList = productService.getTopSellingProducts();
        model.addAttribute("products", productList);
        return "user/home";
    }


}
