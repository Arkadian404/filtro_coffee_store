package com.data.filtro.controller.admin;

import com.data.filtro.model.Product;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/addProduct")
    public String showProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "admin/product/addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") Product product,
                             @RequestParam("file") MultipartFile file,
                             Model model) throws Exception {

        productService.addProduct(product, file);

        model.addAttribute("product", product);
        return "admin/product/list";
    }

//    @GetMapping("/list")
//    public String show(Model model) {
//        Product newProduct = (Product) model.asMap().get("newProduct");
//        if (newProduct != null) {
//            model.addAttribute("products", Collections.singletonList(newProduct));
//        } else {
//            model.addAttribute("products", productService.getAll());
//        }
//        return "admin/product/list";
//    }
}
