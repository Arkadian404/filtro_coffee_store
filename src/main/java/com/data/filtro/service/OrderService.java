package com.data.filtro.service;

import com.data.filtro.model.*;
import com.data.filtro.repository.OrderDetailRepository;
import com.data.filtro.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;


    public Order placeOrder(User user, String phone, String email, String address, String city, int zip, PaymentMethod paymentMethod, List<CartItem> cartItemList) {
        Order order = new Order();
        order.setUser(user);
        if (user.getAddress() == null || !user.getAddress().equals(address)) {
            order.setAddress(address);
        } else {
            order.setAddress(user.getAddress());
        }
        if (user.getPhoneNumber() == null || !user.getPhoneNumber().equals(phone)) {
            order.setPhoneNumber(phone);
        } else {
            order.setPhoneNumber(user.getPhoneNumber());
        }
        if (email.isBlank() || email.isEmpty()) {
            order.setEmail(null);
        } else if (user.getEmail() == null || !user.getEmail().equals(email)) {
            order.setEmail(email);
        } else {
            order.setEmail(user.getEmail());
        }
        if (user.getCity() == null || !user.getCity().equals(city)) {
            order.setCity(city);
        } else {
            order.setCity(user.getCity());
        }
        if (user.getZip() == null || !user.getZip().equals(zip)) {
            order.setZip(zip);
        } else {
            order.setZip(user.getZip());
        }
        order.setOrderDate(new Date());
        order.setPaymentMethod(paymentMethod);
        order.setStatus(1);
        orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        int total = 0;
        for (CartItem cartItem : cartItemList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetail.setTotal(cartItem.getTotal());
            orderDetails.add(orderDetail);
            orderDetailRepository.save(orderDetail);
            total += orderDetail.getTotal();
        }
        order.setTotal(total);
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
        return order;
    }

    public List<Order> getOrderByUserId(int id) {
        return orderRepository.findOrderByUserId(id);
    }

    public Order getCurrentOrderByCartId(int id) {
        return orderRepository.finCurrentdOrderByCartId(id);
    }

    public Order getOrderById(int id) {
        return orderRepository.findOrderById(id);
    }


    public int checkOrderStatusById(int id) {
        return orderRepository.checkOrderStatusById(id);
    }

    public List<Order> getAllVerifiedOrders() {
        return orderRepository.findAllVerfiedOrders();
    }

    public void updateOrderStatus(int orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {

            int status = order.getStatus();
            if (status == 2) {
//                try {
//                    Timer timer = new Timer();
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            order.setStatus(3);
//                            orderRepository.save(order);
//                            System.out.println("Order status updated to shipped");
//                            System.out.println("Current time: " + System.currentTimeMillis());
//                            timer.schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    order.setStatus(4);
//                                    orderRepository.save(order);
//                                    System.out.println("Order status updated to delivered");
//                                }
//                            }, 300000L);
//                        }
//                    }, 60000L);
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
                try {
                    Timer timer = new Timer();
                    TimerTask shippedTask = new TimerTask() {
                        @Override
                        public void run() {
                            order.setStatus(3);
                            orderRepository.save(order);
                            System.out.println("Order status updated to shipped");
                            System.out.println("Current time: " + System.currentTimeMillis());
                            TimerTask deliveredTask = new TimerTask() {
                                @Override
                                public void run() {
                                    order.setStatus(4);
                                    orderRepository.save(order);
                                    System.out.println("Order status updated to delivered");
                                }
                            };
                            timer.schedule(deliveredTask, 300000L);
                            this.cancel();
                        }
                    };
                    timer.schedule(shippedTask, 60000L);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
