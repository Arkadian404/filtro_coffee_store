package com.data.filtro.controller.user;

import com.data.filtro.model.*;
import com.data.filtro.service.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sound.sampled.Port;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@ControllerAdvice
@RequestMapping({"/", "/product", "/category", "/search", "/cart", "/register", "/login"})
public class GlobalController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    FlavorService flavorService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

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

    @ModelAttribute("cartItemList")
    public List<CartItem> cartItemList(HttpSession session) {
        User user = (User) session.getAttribute("user");
        GuestCart guestCart = (GuestCart) session.getAttribute("guestCart");
        if (user != null) {
            Cart cart = cartService.getCartByUserId(user.getId());
            //Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                List<CartItem> cartItemList = cart.getCartItemList();
                return cartItemList;
            }
        } else if (guestCart != null) {
            List<CartItem> cartItemList = guestCart.getCartItemList();
            return cartItemList;
        }
        return null;
    }

    @ModelAttribute("csrfToken")
    public String getCsrfToken(HttpSession session) {
        String csrfToken = (String) session.getAttribute("csrfToken");
        if (csrfToken == null || session.isNew() || csrfToken.isEmpty()) {
            csrfToken = generateCsrfToken();
            session.setAttribute("csrfToken", csrfToken);
            System.out.println(csrfToken);
        }
        return csrfToken;
    }

    private String generateCsrfToken() {
        byte[] tokenBytes = new byte[32]; // 256 bits
        new SecureRandom().nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

}
