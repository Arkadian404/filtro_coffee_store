package com.data.filtro.repository.custom;

import com.data.filtro.model.custom.BestSellingCategories;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BestSellingCategoriesRepository {

    List<BestSellingCategories> findBestSellingCategories(Integer month, Integer year);

}
