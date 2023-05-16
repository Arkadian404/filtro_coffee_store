package com.data.filtro.controller.user;

import com.data.filtro.exception.AuthenticationAccountException;
import com.data.filtro.model.*;
import com.data.filtro.service.CartService;
import com.data.filtro.service.InvoiceService;
import com.data.filtro.service.OrderService;
import jakarta.servlet.http.HttpSession;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/{orderId}")
    public String show(@PathVariable("orderId") int orderId, HttpSession session, Model model) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new AuthenticationAccountException("Đăng nhập để xem đơn hàng của bạn!");
            }
            Order order = orderService.getOrderById(orderId);
            List<OrderDetail> orderDetailList = order.getOrderDetails();
            //Cart cart = cartService.getCurrentOrderCartByUserId(user.getId());
            int check = orderService.checkOrderStatusById(orderId);
//            System.out.println(cart.getId());
//            System.out.println(check);

            System.out.println("order hien tai: " + order.getId());

            System.out.println("orderList theo order: " + order.getId() + "la:");
            orderDetailList.forEach(
                    st -> System.out.println(st.getId())
            );
            model.addAttribute("order", order);
            model.addAttribute("orderDetailList", orderDetailList);
            model.addAttribute("check", check);
        } catch (AuthenticationAccountException ex) {
            model.addAttribute("message", ex.getMessage());
        }
        return "user/invoice";
    }

    @PostMapping("/makeInvoice/{orderId}")
    public String makeInvoice(@PathVariable("orderId") int orderId, HttpSession session, Model model) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new AuthenticationAccountException("Đăng nhập để xem đơn hàng của bạn!");
            }
//            Cart cart = cartService.getCurrentOrderCartByUserId(user.getId());
//            int check = cartService.checkCartStatusByCartId(cart.getId());
//            System.out.println(cart.getId());
//            System.out.println(check);

            //Order order = orderService.getCurrentOrderByCartId(cart.getId());
            Order order = orderService.getOrderById(orderId);
            //System.out.println(order.getId());
            invoiceService.makeInvoice(order);
//            orderService.updateOrderStatus(orderId);
//            orderService.updateSoldByOrderStatus(order);
        } catch (AuthenticationAccountException ex) {
            model.addAttribute("message", ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "redirect:/";
    }

}
