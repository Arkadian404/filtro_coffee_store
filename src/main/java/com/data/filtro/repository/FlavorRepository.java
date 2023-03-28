package com.data.filtro.repository;

import com.data.filtro.model.Flavor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlavorRepository extends JpaRepository<Flavor, Integer> {
}
