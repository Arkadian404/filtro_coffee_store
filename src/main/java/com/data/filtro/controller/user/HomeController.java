package com.data.filtro.controller.user;

import com.data.filtro.model.*;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    ProductService productService;

    @GetMapping
    public String home(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        GuestCart guestCart = (GuestCart) session.getAttribute("guestCart");
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        Account account = (Account) session.getAttribute("account");

        if (guestCart != null && session.getId().equals(request.getRequestedSessionId())) {
            // The cartItemList is associated with the same session as the main path ("/")
            System.out.println("IS GUEST CART!!!");
            // Other logic for the home page
            // ...
        } else {
            System.out.println("NOOOOOOOOOOOOOOOO");
        }
        if (cart != null && session.getId().equals(request.getRequestedSessionId())) {
            // The cartItemList is associated with the same session as the main path ("/")
            System.out.println("IS GUEST CART!!!");
            // Other logic for the home page
            // ...
        } else {
            System.out.println("NOOOOOOOOOOOOOOOO");
        }
        if (user != null && session.getId().equals(request.getRequestedSessionId())) {
            // The cartItemList is associated with the same session as the main path ("/")
            System.out.println("IS USER CART!!!");
            // Other logic for the home page
            // ...
        } else {
            System.out.println("NOOOOOOOOOOOOOOOO");
        }

        if (account != null && session.getId().equals(request.getRequestedSessionId())) {
            // The cartItemList is associated with the same session as the main path ("/")
            System.out.println("IS ACCOUNTT !!!");
            // Other logic for the home page
            // ...
        } else {
            System.out.println("NOOOOOOOOOOOOOOOO");
        }

        List<Product> productList = productService.getTopSellingProducts();
        model.addAttribute("products", productList);
        return "user/home";
    }


}
