package com.data.filtro.controller;

import com.data.filtro.Util.Utility;
import com.data.filtro.exception.UserNotFoundException;
import com.data.filtro.model.Account;
import com.data.filtro.model.User;
import com.data.filtro.service.AccountService;
import com.data.filtro.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.mail.javamail.*;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpRequest;
import java.util.UUID;

@Controller
public class PasswordResetController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/forgot-password")
    public String showForgotPassword() {

        return "forgotPassword";
    }


    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, HttpServletRequest request, Model model) {
        String token = UUID.randomUUID().toString();
        try {
            accountService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset-password?token=" + token;
            sendMail(email, resetPasswordLink);
        } catch (UserNotFoundException ex) {
            model.addAttribute("message", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException exception) {
            model.addAttribute("message", "Lỗi khi gửi mail");
        }
        return "forgotPassword";
    }


    public void sendMail(String recipentAddress, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("contact@ark.com", "Arkadian");
        helper.setTo(recipentAddress);
        String subject = "Đặt lại mật khẩu";
        String content = "Đường link dưới đây dùng để đặt lại mật khẩu\n" + link;
        helper.setSubject(subject);
        helper.setText(content);
        mailSender.send(message);
    }

    @GetMapping("/reset-password")
    public String showPasswordReset(@RequestParam(value = "token") String token, Model model) {
        Account account = accountService.getByPasswordResetToken(token);
        model.addAttribute("token", token);
        if (account == null) {
            model.addAttribute("message", "Token không hợp lệ");
            return "passwordReset";
        }
        return "passwordReset";
    }


    @PostMapping("/reset-password")
    public String processPasswordReset(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       Model model) {
        Account account = accountService.getByPasswordResetToken(token);
        if (account == null) {
            model.addAttribute("message", "Token không hợp lệ");
            return "passwordReset";
        } else {
            accountService.updatePassword(account, password);
            model.addAttribute("message", "Đổi mật khẩu thành công!");
        }
        return "passwordReset";
    }

}
