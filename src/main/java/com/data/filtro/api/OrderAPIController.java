package com.data.filtro.api;

import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.Order;
import com.data.filtro.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderAPIController {


    @Autowired
    OrderService orderService;

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

}
