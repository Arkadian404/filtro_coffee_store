package com.data.filtro.controller.user;

import com.data.filtro.exception.AuthenticationAccountException;
import com.data.filtro.model.*;
import com.data.filtro.service.CartService;
import com.data.filtro.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @GetMapping
    public String show(HttpSession session, Model model) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new AuthenticationAccountException("Đăng nhập để xem đơn hàng của bạn!");
            }
            Cart cart = cartService.getCurrentOrderCartByUserId(user.getId());
            int check = cartService.checkCartStatusByCartId(cart.getId());
            System.out.println(cart.getId());
            System.out.println(check);
            Order order = orderService.getOrderByCartId(cart.getId());
            List<OrderDetail> orderDetailList = order.getOrderDetails();
            model.addAttribute("order", order);
            model.addAttribute("orderDetailList", orderDetailList);
            model.addAttribute("check", check);
        } catch (AuthenticationAccountException ex) {
            model.addAttribute("message", ex.getMessage());
        }
        return "user/invoice";
    }

}
