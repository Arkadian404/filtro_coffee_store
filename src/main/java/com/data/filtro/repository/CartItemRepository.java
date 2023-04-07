package com.data.filtro.repository;

import com.data.filtro.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Query("select ci from CartItem ci where ci.cart.id = :id")
    List<CartItem> findCartItemByCart(@Param("id") int id);

    @Modifying
    @Query("delete from CartItem  ci where ci.cart.id = :cartId and ci.product.id = :productId ")
    void removeCartItemByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);

    @Query("select ci  from CartItem  ci where ci.cart.id = :cartId and ci.product.id = :productId ")
    List<CartItem> getByCartAndProduct(@Param("cartId") int cartId, @Param("productId") int productId);


    void deleteByCartIdAndProductId(int cartId, int productId);
}
