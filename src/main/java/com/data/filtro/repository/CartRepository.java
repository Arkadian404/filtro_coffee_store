package com.data.filtro.repository;

import com.data.filtro.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c where c.user.id = :userId and c.status = 1")
    Cart findCartByUserId(@Param("userId") int userId);

    @Modifying
    @Query("update Cart c set c.status = 0 where c.id =:cartId")
    void removeCartByCartId(@Param("cartId") int cartId);


    @Query("select c.status from Cart c where c.id =:cartId")
    int checkCartStatusByCartId(@Param("cartId") int cartId);


}
