package com.data.filtro.controller.user;

import com.data.filtro.model.*;
import com.data.filtro.repository.CartRepository;
import com.data.filtro.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {


    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartService cartService;

    @Autowired
    GuestCartService guestCartService;

    @Autowired
    CartItemService cartItemService;

//    @GetMapping
//    public String show(HttpSession session, Model model) {
//        User user = (User) session.getAttribute("user");
//        if (user != null) {
//            Cart cart = cartService.getCartByUserId(user.getId());
//            List<CartItem> cartItemList = cart.getCartItemList();
//            model.addAttribute("cartItemList", cartItemList);
//        } else {
//
//        }
//        return "user/cart";
//    }
//
//
//    @PostMapping("/add")
//    public String addCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, HttpSession session, Model model) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart == null) {
//            //String tempCartId = UUID.randomUUID().toString();
//
//            // deal with unauthenticated user cart
//
//            User user = (User) session.getAttribute("user");
//            if (user == null) {
//
//
//            }
//            cart = cartService.createCart(user);
//            session.setAttribute("cart", cart);
//        }
//        cartService.addProductToCart(cart, productId, quantity);
//        return "redirect:/product" + productId;
//    }


    @GetMapping
    public String showCart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        GuestCart guestCart = (GuestCart) session.getAttribute("guestCart");

        if (user != null) {
            Cart cart = cartService.getCurrentCartByUserId(user.getId());
            if (cart != null) {
                List<CartItem> cartItemList = cart.getCartItemList();
                model.addAttribute("cartItemList", cartItemList);
                model.addAttribute("cart", cart);
                session.getAttribute("cart");
                cartItemList.stream().forEach(cartItem -> System.out.println(cartItem.getQuantity()));
            }
        } else if (guestCart != null) {
            List<CartItem> cartItemList = guestCart.getCartItemList();
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("guestCart", guestCart);
        } else {
            model.addAttribute("cartItemList", new ArrayList<CartItem>());
            model.addAttribute("message", "Không có sản phẩm nào trong giỏ hàng!");
        }
//        List<CartItem> test = cartItemService.findAllByCartIdAndProduct(2, 10);
//        test.forEach(cartItem -> System.out.println(cartItem.getProduct().getProductName()));
        return "user/cart";
    }

    @PostMapping("/add")
    public String addCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, HttpSession session, HttpServletRequest request) throws URISyntaxException {
        User user = (User) session.getAttribute("user");
        GuestCart guestCart = (GuestCart) session.getAttribute("guestCart");
        String referer = request.getHeader("Referer");
        System.out.println(referer);
        URI uri = new URI(referer);
        System.out.println(uri);
        String path = uri.getPath();
        System.out.println(path);
        Cart cart = null;
        if (user != null) {
            //cart = cartService.getCartByUserId(user.getId());
            cart = cartService.getCurrentCartByUserId(user.getId());
            if (cart == null) {
                cart = cartService.createCart(user);
                session.setAttribute("cart", cart);
            }
            cartService.addProductToCart(cart, productId, quantity);
        } else if (guestCart != null) {
            cartService.addProductToGuestCart(guestCart, productId, quantity);
            return "redirect:/product/" + productId;
        } else {
            if (guestCart == null) {
                guestCart = cartService.createGuestCart();
                session.setAttribute("guestCart", guestCart);
            }
            cartService.addProductToGuestCart(guestCart, productId, quantity);
        }
        if (path.equals("/")) {
            return "redirect:/";
        } else if (path.startsWith("/category")) {
            return "redirect:" + path;
        } else
            return "redirect:/product/" + productId;

    }


    @PostMapping("/remove/{productId}")
    public String removeCartItem(@PathVariable("productId") int productId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        GuestCart guestCart = (GuestCart) session.getAttribute("guestCart");
        if (user != null) {
            //Cart cart = cartService.getCartByUserId(user.getId());
            Cart cart = cartService.getCurrentCartByUserId(user.getId());
            cartItemService.removeCartItemByCartIdAndProductId(cart.getId(), productId);
            //List<CartItem> cartItemList = cart.getCartItemList();
            // cartItemList.forEach(ci -> System.out.println("USER: " + ci.getProduct().getProductName()));
        } else if (guestCart != null) {
            cartItemService.removeCartItemByGuestCartIdAndProductId(guestCart.getId(), productId);
            guestCart.setCartItemList(cartItemService.getCartItemByGuestCartId(guestCart.getId()));
            session.setAttribute("guestCart", guestCart);
        }
        return "redirect:/cart";
    }

    @ModelAttribute("sum")
    public int sumOfProducts(HttpSession session) {
        User user = (User) session.getAttribute("user");
        GuestCart guestCart = (GuestCart) session.getAttribute("guestCart");
        if (user != null) {
            //Cart cart = cartService.getCartByUserId(user.getId());
            Cart cart = cartService.getCurrentCartByUserId(user.getId());
            if (cart != null) {
                return cartService.totalOfCartItem(user);
            } else {
                return 0;
            }
        } else if (guestCart != null) {
            return cartService.totalOfCartItemTemp(guestCart.getId());
        }
        return 0;
    }

}
