package com.data.filtro.service;

import com.data.filtro.exception.AccountNameExistException;
import com.data.filtro.exception.PasswordDoNotMatchException;
import com.data.filtro.model.Account;
import com.data.filtro.model.User;
import com.data.filtro.repository.AccountRepository;
import com.data.filtro.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    public User getUserByAccountId(int accountId) {
        return userRepository.findByAccountId(accountId);
    }


    public void updateUser(User newUser) throws NotFoundException, ParseException {
        User user = userRepository.findById(newUser.getId()).get();
        if (user == null) {
            throw new NotFoundException("Không tìm thấy tài khoản thích hơp!");
        }

        user.setId(newUser.getId());

        if (newUser.getName() != null) {
            user.setName(newUser.getName());
        }

        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }
        if (newUser.getAddress() != null) {
            user.setAddress(newUser.getAddress());
        }
        if (newUser.getZip() != null) {
            user.setZip(newUser.getZip());
        }
        if (newUser.getPhoneNumber() != null) {
            user.setPhoneNumber(newUser.getPhoneNumber());
        }
        if (newUser.getDob() != null) {
            user.setDob(newUser.getDob());
        }
        if (newUser.getCity() != null) {
            user.setCity(newUser.getCity());
        }
        if (newUser.getSex() != null) {
            user.setSex(newUser.getSex());
        }

        if (newUser.getAccount() != null) {
            Account account = user.getAccount();
            account.setAccountName(newUser.getAccount().getAccountName());
            accountRepository.save(account);
        }
        userRepository.save(user);

    }

    public User getByIdWithAccount(int id) {
        return userRepository.findByIdWithAccount(id);
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
