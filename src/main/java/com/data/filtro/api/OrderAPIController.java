package com.data.filtro.api;

import com.data.filtro.model.*;
import com.data.filtro.service.CartService;
import com.data.filtro.service.OrderService;
import com.data.filtro.service.PaymentMethodService;
import com.data.filtro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderAPIController {


    @Autowired
    OrderService orderService;


    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    PaymentMethodService paymentMethodService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> find(@PathVariable int id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            String message = "Order not found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Order> orders = orderService.getAll();
        if (orders == null) {
            String message = "Orders not found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestParam int userId,
                                        @RequestParam String phone,
                                        @RequestParam String email,
                                        @RequestParam String address,
                                        @RequestParam String city,
                                        @RequestParam int zip,
                                        @RequestParam int paymentId) {
        User user = userService.getByUserId(userId);
        if (user != null) {
            Cart cart = cartService.getCartByUserId(user.getId());
            if (cart == null) {
                return new ResponseEntity<>("Cart ko hop le!", HttpStatus.BAD_REQUEST);
            }
            List<CartItem> cartItemList = cart.getCartItemList();
            PaymentMethod payment = paymentMethodService.getPaymentMethodById(paymentId);
            Order order = orderService.placeOrder(user, phone, email, address, city, zip, payment,
                    cartItemList);
            cartService.removeCartByCartId(cart.getId());
            return new ResponseEntity<>("Đặt hàng thành công " + order.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Dat hang khong thanh cong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/findByStatusOrder")
    public ResponseEntity<?> getOrderByStatusOrder(@RequestParam int status, @RequestParam int userId) {
        List<Order> listOrder = orderService.getOrderByStatusOrder(status, userId);
        if (listOrder == null) {
            String message = "Order not found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listOrder, HttpStatus.OK);
    }
}
