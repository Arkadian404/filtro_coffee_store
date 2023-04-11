package com.data.filtro.repository;

import com.data.filtro.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where o.user.id =:userId")
    List<Order> findOrderByUserId(@Param("userId") int userId);

    @Query("select o from Order o where o.user.cart.id =:cartId")
    Order findOrderByCartId(@Param("cartId") int cartId);
}
