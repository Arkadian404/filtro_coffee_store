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

    @Query("select u from User u where u.account.id =:accountId")
    User findByAccountId(@Param("accountId") int accountId);


    @Query("SELECT u FROM User u JOIN FETCH u.account JOIN FETCH u.cart  WHERE u.id = :userId order by u.cart.createdDate desc limit 1")
    User findByIdWithAccount(@Param("userId") Integer userId);
}
