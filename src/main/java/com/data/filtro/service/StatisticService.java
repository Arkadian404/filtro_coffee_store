package com.data.filtro.service;

import com.data.filtro.model.custom.BestSellingCategories;
import com.data.filtro.model.custom.BestSellingFlavors;
import com.data.filtro.model.custom.BestSellingProducts;
import com.data.filtro.repository.InvoiceRepository;
import com.data.filtro.repository.custom.BestSellingCategoriesRepository;
import com.data.filtro.repository.custom.BestSellingFlavorsRepository;
import com.data.filtro.repository.custom.BestSellingProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {

    @Autowired
    BestSellingProductsRepository bestSellingProductsRepository;

    @Autowired
    BestSellingCategoriesRepository bestSellingCategoriesRepository;

    @Autowired
    BestSellingFlavorsRepository bestSellingFlavorsRepository;

    public List<BestSellingProducts> getBestSellingProducts(Integer month, Integer year) {
        return bestSellingProductsRepository.findBestSellingProducts(month, year);
    }


    public List<BestSellingCategories> getBestSellingCategories(Integer month, Integer year) {
        return bestSellingCategoriesRepository.findBestSellingCategories(month, year);
    }

    public List<BestSellingFlavors> getBestSellingFlavors(Integer month, Integer year) {
        return bestSellingFlavorsRepository.findBestSellingFlavors(month, year);
    }


}
