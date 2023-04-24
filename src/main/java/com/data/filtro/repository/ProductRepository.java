package com.data.filtro.repository;

import com.data.filtro.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    @Query("select p from Product p where p.id =:id")
    Product findById(@Param("id") int id);

    @Query("select p from Product p order by p.sold desc limit 6")
    List<Product> findTop6SellingProducts();

    @Query("select p from Product p order by p.discount desc limit 3")
    List<Product> findTop3MostDiscountProducts();

    Page<Product> findAll(Pageable pageable);

    @Query("select p from Product p where p.category.id = :categoryId")
    Page<Product> findProductsByCategory(@Param("categoryId") Integer id, Pageable pageable);

    @Query("select p from Product p where p.flavor.id = :flavorId")
    Page<Product> findProductsByFlavor(@Param("flavorId") Integer id, Pageable pageable);

    @Query("select p from Product p where p.flavor.id = :flavorId and p.id != :currentProductId order by p.productName limit 4")
    List<Product> findTop4ProductsByFlavor(@Param("flavorId") Integer id, @Param("currentProductId") Integer currentProductId);

    @Query("select  p from  Product  p where lower(p.productName) like %:name%")
    Page<Product> findProducsByName(@Param("name") String name, Pageable pageable);

}
