package com.data.filtro.repository;

import com.data.filtro.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p order by p.sold desc limit 6")
    List<Product> findTopSellingProducts();
}
