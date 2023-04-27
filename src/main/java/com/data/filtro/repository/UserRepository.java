package com.data.filtro.repository;

import com.data.filtro.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //@Query("select distinct c.user from Cart c")
    //@Query("select distinct c.user from Cart c join fetch User u")
    //@Query("select u from User u inner join User on u.id in (select distinct c.user.id from Cart c)")
    //@Query("SELECT DISTINCT u FROM User u LEFT JOIN fetch Cart c on u.id = c.user.id order by u.id")
    //@Query("SELECT distinct u FROM User u LEFT JOIN FETCH u.cart c GROUP BY u.id")
    List<User> findAll();


    @Query("select u from User u where u.id =:id")
    User findUserById(@Param("id") int id);

    @Query("select u from User u where u.email =:email")
    User findByEmail(@Param("email") String email);

    @Query("select u from User u where u.name =:name")
    User findByUserName(@Param("name") String name);

    @Query("select u from User u where u.account.id =:accountId")
    User findByAccountId(@Param("accountId") int accountId);

    @Query("SELECT u FROM User u JOIN FETCH u.account JOIN FETCH u.cart  WHERE u.id = :userId order by u.cart.createdDate desc limit 1")
    User findByIdWithAccount(@Param("userId") Integer userId);

    @Query("select u from User u")
    Page<User> findAll(Pageable pageable);
}
