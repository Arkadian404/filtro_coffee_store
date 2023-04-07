package com.data.filtro.repository;

import com.data.filtro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email =:email")
    User findByEmail(@Param("email") String email);

    @Query("select u from User u where u.name =:name")
    User findByUserName(@Param("name") String name);
}
