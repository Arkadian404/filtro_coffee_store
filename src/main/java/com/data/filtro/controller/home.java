package com.data.filtro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class home {

    @GetMapping
    public String home(){
        return "home";
    }

    @GetMapping("/hihi")
    public String hihi(){
        return "hihi";
    }

}
