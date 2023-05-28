package com.data.filtro.service;

import org.springframework.stereotype.Service;

@Service
public class InputService {
    public static boolean containsAllowedCharacters(String input) {
        // Biểu thức chính quy để so khớp với chuỗi chỉ chứa các ký tự được phép
        String pattern = "^[a-zA-Z0-9@()]+$";

        // Kiểm tra so khớp
        return input.matches(pattern);
    }

    public static boolean isStringLengthLessThan50(String input) {
        return input.length() < 50;
    }


    public boolean isValidComment(String input) {
        return input.matches("^[\\p{L}@(),.!\\s]{1,100}$");
    }
}
