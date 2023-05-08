package com.data.filtro.repository.custom;

import com.data.filtro.model.custom.BestSellingCategories;
import com.data.filtro.model.custom.BestSellingFlavors;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BestSellingFlavorsRepository {

    List<BestSellingFlavors> findBestSellingFlavors(Integer month, Integer year);

}
