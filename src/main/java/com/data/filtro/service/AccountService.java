package com.data.filtro.service;

import com.data.filtro.exception.AuthenticationAccountException;
import com.data.filtro.exception.PasswordDoNotMatchException;
import com.data.filtro.exception.UserNotFoundException;
import com.data.filtro.model.Account;
import com.data.filtro.model.User;
import com.data.filtro.repository.AccountRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountService {
    private final UserService userService;
    @Autowired
    AccountRepository accountRepository;


    @Autowired
    public AccountService(UserService userService) {
        this.userService = userService;
    }

    public Page<Account> getAllPaging(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Account getAccountByName(String accountName) {
        return accountRepository.findAccountByName(accountName);
    }

    public Account getAccountById(int id) {
        return accountRepository.findById(id);
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

    public Account authenticateAdmin(String accountName, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account tempAccount = getAccountByName(accountName.trim());
        if (tempAccount != null) {
            if (passwordEncoder.matches(password, tempAccount.getPassword())) {
                return accountRepository.authenticateAdmin(accountName, tempAccount.getPassword());
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

    public void create(Account account) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account newAccount = new Account();
        newAccount.setAccountName(account.getAccountName());
        newAccount.setPassword(passwordEncoder.encode(account.getPassword()));
        newAccount.setCreatedDate(new Date());
        newAccount.setRoleNumber(account.getRoleNumber());
        newAccount.setStatus(account.getStatus());
        accountRepository.save(newAccount);
    }

    public void update(Account account) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account newAccount = getAccountById(account.getId());
        newAccount.setAccountName(account.getAccountName());
        if (!account.getPassword().isEmpty()) { // check if password has been changed
            String encodedPassword = passwordEncoder.encode(account.getPassword());
            newAccount.setPassword(encodedPassword);
        }
        newAccount.setCreatedDate(new Date());
        newAccount.setRoleNumber(account.getRoleNumber());
        newAccount.setStatus(account.getStatus());
        accountRepository.save(newAccount);
    }


    public void delete(int id) {
        accountRepository.deleteById(id);
    }

    public List<Account> getAppropriateAccountForUser() {
        return accountRepository.findAppropriateAccountForUser();
    }

    public List<Account> getEligibleAccountForStaff() {
        return accountRepository.findEligibleAccountForStaff();
    }

}
