package com.data.filtro.repository.custom;

import com.data.filtro.model.custom.BestSellingProducts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface BestSellingProductsRepository {
    List<BestSellingProducts> findBestSellingProducts(Integer month, Integer year);

}
