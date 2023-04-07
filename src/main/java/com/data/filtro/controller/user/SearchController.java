package com.data.filtro.controller.user;

import com.data.filtro.model.Flavor;
import com.data.filtro.model.Product;
import com.data.filtro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/search")
public class SearchController {


    @Autowired
    ProductService productService;


    @ModelAttribute(name = "discountProducts")
    public List<Product> getDiscountProducts(Model model) {
        List<Product> productList = productService.getTopDiscountProducts();
        return productList;
    }

    @GetMapping("")
    public String showSearchPage(@RequestParam String name,
                                 @RequestParam(defaultValue = "best_selling") String sortType,
                                 @RequestParam(name = "page") Optional<Integer> page,
                                 Model model) {

        int currentPage = page.orElse(1);
        int pageSize = 6;
        Pageable pageable;
        Page<Product> productPage;

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

        productPage = productService.getProductsByName(name, pageable);


        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortType", sortType);
        model.addAttribute("name", name);
        List<Product> p = productPage.getContent();
        p.forEach(product -> System.out.println(product.getProductName()));
        return "user/search";
    }

}
