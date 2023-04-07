package com.data.filtro.controller.user;

import com.data.filtro.model.Product;
import com.data.filtro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping
    public String product() {
        return "user/product";
    }

    @GetMapping("/{id}")
    public String product(@PathVariable Integer id, Model model) {
        int currentProductId = id;
        long maxProductId = productService.countAll();
        int t1 = 13;
        long t2 = 24;
        Product product = productService.getProductById(id);
        List<Product> productList = productService.getTop4ProductsByFlavor(product.getFlavor().getId(), currentProductId);
        model.addAttribute("product", product);
        model.addAttribute("products", productList);
        model.addAttribute("currentProductId", currentProductId);
        model.addAttribute("maxProductId", maxProductId);
        productList.forEach(product1 -> System.out.println(product1.getProductName()));
        return "user/product";
    }
}
