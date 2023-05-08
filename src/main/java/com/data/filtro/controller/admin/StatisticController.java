package com.data.filtro.controller.admin;

import com.data.filtro.model.custom.BestSellingCategories;
import com.data.filtro.model.custom.BestSellingFlavors;
import com.data.filtro.model.custom.BestSellingProducts;
import com.data.filtro.service.InvoiceService;
import com.data.filtro.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/statistic")
public class StatisticController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    StatisticService statisticService;


    @GetMapping
    public String get(@RequestParam(defaultValue = "4") int month, @RequestParam(defaultValue = "2023") int year, Model model) {
        System.out.println(month + "test" + year);
        Integer revenue = invoiceService.getRevenuePerMonthAndYear(month, year);
        System.out.println(revenue);
        List<BestSellingProducts> bestSellingProducts = statisticService.getBestSellingProducts(month, year);
        List<BestSellingCategories> bestSellingCategories = statisticService.getBestSellingCategories(month, year);
        List<BestSellingFlavors> bestSellingFlavors = statisticService.getBestSellingFlavors(month, year);
        model.addAttribute("revenue", revenue);
        model.addAttribute("bestSellingProducts", bestSellingProducts);
        model.addAttribute("bestSellingCategories", bestSellingCategories);
        model.addAttribute("bestSellingFlavors", bestSellingFlavors);
        return "admin/statistic";
    }


}
