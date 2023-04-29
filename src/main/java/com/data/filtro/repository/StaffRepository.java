package com.data.filtro.repository;

import com.data.filtro.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

    @Query("select s from Staff s where s.id =:id")
    Staff findById(@Param("id") int id);

    @Modifying
    @Query("update Staff s set s.status = 0 where s.id =:id")
    void delete(@Param("id") int id);

    @Override
    Page<Staff> findAll(Pageable pageable);
}
