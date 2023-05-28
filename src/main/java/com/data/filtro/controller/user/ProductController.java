package com.data.filtro.controller.user;

import com.data.filtro.model.Feedback;
import com.data.filtro.model.Product;
import com.data.filtro.model.User;
import com.data.filtro.service.FeedbackService;
import com.data.filtro.service.InputService;
import com.data.filtro.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    InputService inputService;

    private String errorMessage;

    @GetMapping
    public String product() {
        return "user/product";
    }

    @GetMapping("/{id}")
    public String product(@PathVariable Integer id, HttpSession session, Model model) {
        int currentProductId = id;
        long maxProductId = productService.countAll();
        List<Feedback> feedbackList = feedbackService.getAllFeedBackByProductId(id);
        int t1 = 13;
        long t2 = 24;
        Product product = productService.getProductById(id);
        List<Product> productList = productService.getTop4ProductsByFlavor(product.getFlavor().getId(), currentProductId);
        model.addAttribute("product", product);
        model.addAttribute("products", productList);
        model.addAttribute("currentProductId", currentProductId);
        model.addAttribute("maxProductId", maxProductId);
        model.addAttribute("feedbackList", feedbackList);
        productList.forEach(product1 -> System.out.println(product1.getProductName()));

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            System.out.println(errorMessage);
        }
        errorMessage = null;

        return "user/product";
    }


    @PostMapping("/{id}/feedback")
    public String feedback(@ModelAttribute Feedback feedback, @PathVariable Integer id, HttpSession session, Model model) {
        if (!inputService.isValidComment(feedback.getContent())) {
            String message = "Bình luận chỉ được chứa các ký tự thường và dấu chấm, dấu phẩy, (), @, ! và " +
                    "độ dài dưới 50 ký tự";
            model.addAttribute("errorMessage", message);
            errorMessage = message;
            return "redirect:/product/" + id;
        }

        User user = (User) session.getAttribute("user");
        Product product = productService.getProductById(id);
        feedbackService.createFeedback(user, product, feedback);
        return "redirect:/product/" + id;
    }
}
