package com.data.filtro.service;

import com.data.filtro.exception.AccountNameExistException;
import com.data.filtro.exception.PasswordDoNotMatchException;
import com.data.filtro.model.Account;
import com.data.filtro.model.User;
import com.data.filtro.repository.AccountRepository;
import com.data.filtro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;


@Service
public class UserService {
    private final AccountService accountService;

    @Autowired
    public UserService(@Lazy AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }


    public User getUserByName(String name) {
        return userRepository.findByUserName(name);
    }


    public void registerUser(String userName, String accountName, String email, String password, String repeatPassword) {

        if (accountService.checkAccountName(accountName)) {
            throw new AccountNameExistException("Tên tài khoản đã được đặt");
        }

        if (!password.equals(repeatPassword)) {
            throw new PasswordDoNotMatchException("Không đúng mật khẩu !");
        }

        User user = new User();
        user.setName(userName);
        user.setEmail(email);
        userRepository.save(user);
        Account account = new Account();
        account.setAccountName(accountName);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPassword = passwordEncoder.encode(password);
        account.setPassword(hashPassword);
        account.setCreatedDate(new Date());
        account.setRoleNumber(3);
        account.setStatus(1);
        user.setAccount(account);
        accountRepository.save(account);
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


//    public void sendMail(String userMailAdress, String subject, String message) {
//        SimpleMailMessage mail = new SimpleMailMessage();
//        JavaMailSender javaMailSender = new JavaMailSenderImpl();
//        mail.setTo(userMailAdress);
//        mail.setSubject(subject);
//        mail.setText(message);
//        mail.setFrom("phutv1990@gmail.com");
//        javaMailSender.send(mail);
//    }
}
