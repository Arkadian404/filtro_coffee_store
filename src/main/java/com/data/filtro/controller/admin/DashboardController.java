package com.data.filtro.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/admin", "/"})
public class DashboardController {

    @GetMapping
    public String show() {


        return "admin/a";
    }


    @GetMapping("/b")
    public String showB() {


        return "admin/b";
    }


    @GetMapping("/c")
    public String showC() {


        return "admin/c";
    }

}
