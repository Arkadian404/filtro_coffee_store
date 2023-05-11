package com.data.filtro.api;

import com.data.filtro.exception.AccountNameExistException;
import com.data.filtro.exception.PasswordDoNotMatchException;
import com.data.filtro.model.Account;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.service.AccountService;
import com.data.filtro.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountAPIController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<Account> authenticate(@RequestParam String accountName, @RequestParam String password) {
        Account account = accountService.authenticateUser(accountName, password);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> find(@PathVariable int id) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
            String message = "No account found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String userName, @RequestParam String accountName, @RequestParam String email, @RequestParam String password, @RequestParam String repeatPassword) {
        try {
            userService.registerUser(userName, accountName, email, password, repeatPassword);
            String message = "Tao tai khoan thanh cong!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (AccountNameExistException ex) {
            String message = "Tai khoan da ton tai";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        } catch (PasswordDoNotMatchException ex) {
            String message = "Mat khau khong dung";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
    }


}
