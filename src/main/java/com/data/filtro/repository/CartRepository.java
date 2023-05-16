package com.data.filtro.repository;

import com.data.filtro.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c where c.user.id = :userId and c.status = 1")
    Cart findCurrentCartByUserId(@Param("userId") int userId);

    @Query("select c from Cart c where c.user.id =:userId")
    Cart findCartByUserId(@Param("userId") int userId);

    @Query("select  c from Cart  c where c.id =:id ")
    Cart findCartById(@Param("id") int id);


    @Query("select c from Cart c where c.user.id = :userId and c.status = 0")
    List<Cart> findAllOrderCartByUserId(@Param("userId") int userId);

    @Query("select c from Cart c where c.user.id = :userId and c.status = 0 order by c.createdDate desc limit 1")
    Cart findCurrentOrderCartByUserId(@Param("userId") int userId);

    @Query("select c.status from Cart c where c.id =:cartId")
    int checkCartStatusByCartId(@Param("cartId") int cartId);

    @Modifying
    @Query("delete from Cart c where c.id =:cartId")
    void deleteByCartId(@Param("cartId") int cartId);
}
