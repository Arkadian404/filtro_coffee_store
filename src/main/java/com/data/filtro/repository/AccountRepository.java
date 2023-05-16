package com.data.filtro.repository;

import com.data.filtro.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("select a from Account a where a.accountName = :accountName")
    Account findAccountByName(@Param("accountName") String accountName);


    @Query("select a from Account a  where a.accountName = :accountName and a.password=:password and a.role.id=3")
    Account authenticateUser(@Param("accountName") String acountName, @Param("password") String password);

    @Query("select a from Account a  where a.accountName = :accountName and a.password=:password and a.role.id=1")
    Account authenticateAdmin(@Param("accountName") String accountName, @Param("password") String password);

    @Query("select a from Account a  where a.accountName = :accountName and a.password=:password and a.role.id=4")
    Account authenticateShipper(@Param("accountName") String accountName, @Param("password") String password);


    @Query("select a from Account  a where a.passwordResetToken = :token")
    Account findByPasswordResetToken(@Param("token") String token);

    @Query("select a from  Account  a where a.id =:id")
    Account findById(@Param("id") int id);


    @Query("select a from Account a left join fetch User u on a.id = u.account.id where a.role.id = 3 and u.account.id is null")
    List<Account> findAppropriateAccountForUser();

    @Query("select a from Account a left join fetch User u on a.id = u.account.id where a.role.id != 3")
    List<Account> findEligibleAccountForStaff();

    Page<Account> findAll(Pageable pageable);

}
