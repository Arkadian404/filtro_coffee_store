package com.data.filtro.service;

import com.data.filtro.exception.AuthenticationAccountException;
import com.data.filtro.exception.PasswordDoNotMatchException;
import com.data.filtro.exception.UserNotFoundException;
import com.data.filtro.model.Account;
import com.data.filtro.model.User;
import com.data.filtro.repository.AccountRepository;
import javassist.NotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserService userService;
    @Autowired
    AccountRepository accountRepository;


    @Autowired
    public AccountService(UserService userService) {
        this.userService = userService;
    }


    public Account getAccountByName(String accountName) {
        return accountRepository.findAccountByName(accountName);
    }


    public Account getByPasswordResetToken(String token) {
        return accountRepository.findByPasswordResetToken(token);
    }

    public void updateResetPasswordToken(String token, String email) {
        Account account;
        User user = userService.getUserByEmail(email);
        if (user != null) {
            account = getAccountByName(user.getAccount().getAccountName());
            account.setPasswordResetToken(token);
            accountRepository.save(account);
        } else {
            throw new UserNotFoundException("Không tìm thầy người dùng với địa chỉ email: " + email);
        }
    }

    public void updatePassword(Account account, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPassword = passwordEncoder.encode(newPassword);
        account.setPassword(hashPassword);
        account.setPasswordResetToken(null);
        accountRepository.save(account);

    }

    public boolean checkAccountName(String accountName) {
        Account existingAccount = getAccountByName(accountName.trim());
        if (existingAccount != null)
            return true;
        else
            return false;
    }

    public Account authenticateUser(String accountName, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account tempAccount = getAccountByName(accountName.trim());
        if (tempAccount != null) {
            if (passwordEncoder.matches(password, tempAccount.getPassword())) {
                Account authenticateAccount = accountRepository.authenticate(accountName, tempAccount.getPassword());
                return authenticateAccount;
            } else {
                throw new AuthenticationAccountException("Sai mật khẩu!");
            }
        } else {
            throw new AuthenticationAccountException("Sai tên đăng nhập!");
        }
    }

    public void changePassword(Account account, String currentPassword, String newPassword, String repeatPassword) throws NotFoundException {
        if (account == null) {
            throw new NotFoundException("Không tìm thấy tài khoản thích hơp!");
        }
        if (!newPassword.equals(repeatPassword)) {
            throw new PasswordDoNotMatchException("Không đúng mật khẩu !");
        }
        String userPassword = account.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(currentPassword, userPassword)) {
            String hashPassword = passwordEncoder.encode(newPassword);
            account.setPassword(hashPassword);
            accountRepository.save(account);
        } else {
            throw new AuthenticationAccountException("Sai mật khẩu!");
        }

    }

}
