package com.data.filtro.repository;

import com.data.filtro.model.GuestCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuestCartRepository extends JpaRepository<GuestCart, Integer> {

    @Query("select g from GuestCart g where g.id =:id")
    GuestCart findGuestCartById(@Param("id") int id);
}
