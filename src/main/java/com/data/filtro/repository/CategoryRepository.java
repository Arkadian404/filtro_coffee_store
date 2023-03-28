package com.data.filtro.repository;

import com.data.filtro.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c order by c.id limit 5")
    public List<Category> find5Categories();

}
