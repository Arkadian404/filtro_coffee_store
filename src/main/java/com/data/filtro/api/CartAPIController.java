package com.data.filtro.api;

import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.User;
import com.data.filtro.model.Cart;
import com.data.filtro.service.CartService;
import com.data.filtro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartAPIController {

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @GetMapping("/getUserCart/{id}")
    public ResponseEntity<?> getUserCart(@PathVariable int id) {
        User user = userService.getByUserId(id);
        if (user == null) {
            String message = "User not found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        } else {
            Cart cart = cartService.getCartByUserId(user.getId());
            if (cart == null) {
                cart = cartService.createCart(user);
            }
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
    }

}
