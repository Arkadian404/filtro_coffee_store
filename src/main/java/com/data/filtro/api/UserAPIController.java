package com.data.filtro.api;

import com.data.filtro.model.User;
import com.data.filtro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {

    @Autowired
    UserService userService;

    @GetMapping("/find/{id}")
    public ResponseEntity<User> findUser(@PathVariable("id") int id) {
        try {
            User user = userService.getByUserId(id);
            System.out.println(user.getName());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
