package com.data.filtro.repository;

import com.data.filtro.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findAll(Pageable pageable);

    @Query("select o from Order o where o.user.id =:userId")
    List<Order> findOrderByUserId(@Param("userId") int userId);

    @Query("select o from Order o where o.user.cart.id =:cartId order by o.orderDate desc limit 1")
    Order finCurrentOrderByCartId(@Param("cartId") int cartId);

    @Query("select o from Order o where o.id =:orderId")
    Order findOrderById(@Param("orderId") int orderId);

    @Query("select o.status from Order o where o.id =:orderId")
    int checkOrderStatusById(@Param("orderId") int orderId);

    @Query("select o from Order o where o.status = 2")
    List<Order> findAllVerifiedOrders();

    @Modifying
    @Query("update Order o set o.status = 5 where o.id =:id")
    void updateCancelOrder(@Param("id") int id);

    @Modifying
    @Query("update Order o set o.status=6 where o.id =:id")
    void cancelOrder(@Param("id") int id);

    @Query("select o from Order o where o.status =:status and o.user.id =:userId")
    List<Order> findOrderByStatusOrder(@Param("status") int status, @Param("userId") int userId);

    @Query("select o from Order o where o.status = 3")
    List<Order> findEligibleOrderForShipper();

    @Query("select o from Order o where o.shipper.id=:id")
    List<Order> findOrderByShipperId(@Param("id") int id);

    @Query("select o from Order o where o.status = 4 and o.shipper.account.id =:id")
    List<Order> findShippingOrderByShipperId(@Param("id") int id);


    @Query("select o from Order o where o.status = 5 and o.shipper.account.id =:id")
    List<Order> findDeliveredOrderByShipperId(@Param("id") int id);

    @Query("select o from Order o where o.status = 4 and o.user =:id")
    List<Order> findShippingOrderByUserId(@Param("id") int id);

    @Query("select o from Order o where o.status = 5 and o.user.id =:id")
    List<Order> findDeliveredOrderByUserId(@Param("id") int id);
}
