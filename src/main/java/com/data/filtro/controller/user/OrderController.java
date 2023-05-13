package com.data.filtro.controller.user;

import com.data.filtro.exception.AuthenticationAccountException;
import com.data.filtro.model.*;
import com.data.filtro.service.CartItemService;
import com.data.filtro.service.CartService;
import com.data.filtro.service.OrderService;
import com.data.filtro.service.PaymentMethodService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CartService cartService;

    @Autowired
    PaymentMethodService paymentMethodService;

    @GetMapping
    public String show(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Cart cart = cartService.getCartByUserId(user.getId());
            //Cart cart = cartService.getCurrentCartByUserId(user.getId());
            //Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                List<CartItem> cartItemList = cart.getCartItemList();
                if (user.getAddress() != null && user.getCity() != null && user.getZip() != null && user.getPhoneNumber() != null) {
                    // User has saved information, display it in the form
                    model.addAttribute("address", user.getAddress());
                    model.addAttribute("city", user.getCity());
                    model.addAttribute("zip", user.getZip());
                    model.addAttribute("phone", user.getPhoneNumber());
                }
                cartItemList.forEach(st -> System.out.println(st.getProduct().getProductName()));
                model.addAttribute("cartItemList", cartItemList);
            }

        } else {
            model.addAttribute("message", "ĐĂNG NHẬP DÙM TAO CÁI MÀY!");
        }
        return "user/order";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("zip") int zip,
            @RequestParam("paymentMethod") PaymentMethod paymentMethod,
            HttpSession session,
            Model model
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("Vui lòng đăng nhập trước khi thanh toán");
        }
        Cart cart = cartService.getCartByUserId(user.getId());
        //Cart cart = cartService.getCartByUserId(user.getId());
        List<CartItem> cartItemList = cart.getCartItemList();
        Order order = orderService.placeOrder(user, phone, email, address, city, zip, paymentMethod, cartItemList);
        cartService.removeCartByCartId(cart.getId());
        session.setAttribute("cart", cart);
        int orderId = order.getId();
        return "redirect:/invoice/" + orderId;
    }


    @PostMapping("/cancel")
    public String cancel(@RequestParam int id) {
        orderService.updateCancelOrder(id);
        return "redirect:/user/billing";
    }

    @ModelAttribute("sum")
    public int sumOfProducts(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //Cart cart = cartService.getCartByUserId(user.getId());
            Cart cart = cartService.getCurrentCartByUserId(user.getId());
            if (cart != null) {
                return cartService.totalOfCartItem(user);
            }
        }
        return 0;
    }

    @ModelAttribute("paymentMethods")
    public List<PaymentMethod> paymentMethods() {
        return paymentMethodService.getAllPaymentMethods();
    }

}
