package com.data.filtro.repository;

import com.data.filtro.model.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Integer> {


    @Query("select s from Shipper s where s.id=:id")
    Shipper findById(@Param("id") int id);


    @Query("select s from Shipper s where s.order.id=:orderId")
    Shipper findByOrderId(@Param("orderId") int orderId);

}
