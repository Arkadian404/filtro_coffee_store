package com.data.filtro.repository;

import com.data.filtro.model.Flavor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FlavorRepository extends JpaRepository<Flavor, Integer> {

    @Query("select f from Flavor f where f.id =:id")
    Flavor findById(@Param("id") int id);

    @Modifying
    @Query("update Flavor f set f.status=0 where f.id=:id")
    void deleteById(@Param("id") int id);

    Page<Flavor> findAll(Pageable pageable);
}
