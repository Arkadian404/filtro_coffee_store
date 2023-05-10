package com.data.filtro.api;

import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.User;
import com.data.filtro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {

    @Autowired
    UserService userService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findUser(@PathVariable("id") int id) {
        try {
            User user = userService.getByUserId(id);
            if (user == null) {
                String message = "No user found!";
                ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/find/getAll")
    public ResponseEntity<?> getAll() {
        try {
            List<User> users = userService.getAll();
            if (users == null) {
                String message = "No users found!";
                ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        User newUser = userService.getByUserId(id);
        if (newUser != null) {
            newUser = userService.updateUser(id, user);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } else {
            String message = "Can't be null!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
