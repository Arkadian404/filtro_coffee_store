package com.data.filtro.controller.shipper;

import com.data.filtro.model.Account;
import com.data.filtro.model.Order;
import com.data.filtro.model.Shipper;
import com.data.filtro.service.OrderService;
import com.data.filtro.service.ShipperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/shipper")
public class ShipperController {

    @Autowired
    OrderService orderService;

    @Autowired
    ShipperService shipperService;

    @GetMapping
    public String show(HttpSession session, Model model) {
        Account shipper = (Account) session.getAttribute("shipper");
        if (shipper == null) {
            return "redirect:/shipper/login";
        }
        List<Order> eligibleOrders = orderService.getEligibleOrderForShipper();
        List<Order> shippingOrders = orderService.getShippingOrderByShipperId(shipper.getId());
        List<Order> deliveredOrders = orderService.getDeliveredOrderByShipperId(shipper.getId());
        model.addAttribute("eligibleOrders", eligibleOrders);
        model.addAttribute("shippingOrders", shippingOrders);
        model.addAttribute("deliveredOrders", deliveredOrders);
        return "shipper/home";
    }


    @PostMapping("/shipping")
    public String shipping(@RequestParam int orderId, HttpSession session) {
        Account shipper = (Account) session.getAttribute("shipper");
        Order order = orderService.getOrderById(orderId);
        shipperService.shipping(shipper, order);
        return "redirect:/shipper";
    }

    @PostMapping("/delivered")
    public String delivered(@RequestParam int orderId, HttpSession session) {
        Account shipper = (Account) session.getAttribute("shipper");
        Order order = orderService.getOrderById(orderId);
        shipperService.delivered(orderId);
        return "redirect:/shipper";
    }

}
