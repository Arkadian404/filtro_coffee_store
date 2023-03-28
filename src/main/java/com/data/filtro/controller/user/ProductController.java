package com.data.filtro.controller.user;

import com.data.filtro.model.Product;
import com.data.filtro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public String product(@PathVariable Integer id, Model model) {

        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        return "user/product";
    }
}
