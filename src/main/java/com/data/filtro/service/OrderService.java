package com.data.filtro.service;

import com.data.filtro.model.*;
import com.data.filtro.repository.OrderDetailRepository;
import com.data.filtro.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductService productService;


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

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getCurrentOrderByCartId(int id) {
        return orderRepository.finCurrentOrderByCartId(id);
    }

    public Page<Order> getAllPaging(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public void update(Order order) {
        System.out.println(order.getId());
        Order newOrder = orderRepository.findById(order.getId()).get();
        System.out.println(newOrder.getId());
        newOrder.setPhoneNumber(order.getPhoneNumber());
        newOrder.setAddress(order.getAddress());
        newOrder.setCity(order.getCity());
        newOrder.setZip(order.getZip());
        newOrder.setStatus(order.getStatus());
        orderRepository.save(newOrder);
    }

    @Transactional
    public void delete(int id) {
        orderRepository.cancelOrder(id);
    }

    public Order getOrderById(int id) {
        return orderRepository.findOrderById(id);
    }


    public int checkOrderStatusById(int id) {
        return orderRepository.checkOrderStatusById(id);
    }

    public List<Order> getAllVerifiedOrders() {
        return orderRepository.findAllVerifiedOrders();
    }

    @Transactional
    public void updateCancelOrder(int id) {
        orderRepository.updateCancelOrder(id);
    }

//    public void updateOrderStatus(int orderId) {
//        Order order = getOrderById(orderId);
//        if (order != null) {
//
//            int status = order.getStatus();
//            if (status == 2) {
////                try {
////                    Timer timer = new Timer();
////                    timer.schedule(new TimerTask() {
////                        @Override
////                        public void run() {
////                            order.setStatus(3);
////                            orderRepository.save(order);
////                            System.out.println("Order status updated to shipped");
////                            System.out.println("Current time: " + System.currentTimeMillis());
////                            timer.schedule(new TimerTask() {
////                                @Override
////                                public void run() {
////                                    order.setStatus(4);
////                                    orderRepository.save(order);
////                                    System.out.println("Order status updated to delivered");
////                                }
////                            }, 300000L);
////                        }
////                    }, 60000L);
////
////                } catch (Exception ex) {
////                    ex.printStackTrace();
////                }
//                try {
//                    Timer timer = new Timer();
//                    TimerTask shippedTask = new TimerTask() {
//                        @Override
//                        public void run() {
//                            order.setStatus(3);
//                            orderRepository.save(order);
//                            System.out.println("Order status updated to shipped");
//                            System.out.println("Current time: " + System.currentTimeMillis());
//                            TimerTask deliveredTask = new TimerTask() {
//                                @Override
//                                public void run() {
//                                    order.setStatus(4);
//                                    orderRepository.save(order);
//                                    System.out.println("Order status updated to delivered");
//                                }
//                            };
//                            timer.schedule(deliveredTask, 300000L);
//                            this.cancel();
//                        }
//                    };
//                    timer.schedule(shippedTask, 60000L);
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }

    public void updateOrderShippingStatus(int orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            int status = order.getStatus();
            if (status == 3) {
                order.setStatus(4);
                System.out.println("Update order to 4!!!");
                orderRepository.save(order);
            }
        }
    }


    public void updateOrderDeliveredStatus(int orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            int status = order.getStatus();
            if (status == 4) {
                order.setStatus(5);
                System.out.println("Update order to 5!!!");
                orderRepository.save(order);
            }
        }
    }

    public void updateSoldByOrderStatus(Order order) {
        Order updatedOrder = getOrderById(order.getId());
        if (updatedOrder == null) {
            return;
        }
        int status = updatedOrder.getStatus();
        if (status == 5) {
            updateSold(order.getOrderDetails(), true);
        } else if (status == 7) {
            updateSold(order.getOrderDetails(), false);
        }
    }

    private void updateSold(List<OrderDetail> orderDetails, boolean isIncrease) {
        for (OrderDetail detail : orderDetails) {
            Product product = detail.getProduct();
            int quantity = detail.getQuantity();
            if (isIncrease) {
                product.setSold(product.getSold() + quantity);
            } else {
                product.setSold(product.getSold() - quantity);
            }
            productService.save(product);
        }
    }


    public List<Order> getOrderByStatusOrder(int status, int userId) {
        return orderRepository.findOrderByStatusOrder(status, userId);
    }

    public List<Order> getEligibleOrderForShipper() {
        return orderRepository.findEligibleOrderForShipper();
    }


    public List<Order> getShippingOrderByShipperId(int id) {
        return orderRepository.findShippingOrderByShipperId(id);
    }

    public List<Order> getDeliveredOrderByShipperId(int id) {
        return orderRepository.findDeliveredOrderByShipperId(id);
    }


    public List<Order> getDeliveredOrderByUserId(int id) {
        return orderRepository.findDeliveredOrderByUserId(id);
    }

    public List<Order> getShippingOrderByUserId(int id) {
        return orderRepository.findShippingOrderByUserId(id);
    }


}
