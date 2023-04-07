package com.data.filtro.repository;

import com.data.filtro.model.Account;
import com.data.filtro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("select a from Account a where a.accountName = :accountName")
    Account findAccountByName(@Param("accountName") String accountName);


    @Query("select a from Account a  where a.accountName = :accountName and a.password=:password")
    Account authenticate(@Param("accountName") String acountName, @Param("password") String password);


    @Query("select a from Account  a where a.passwordResetToken = :token")
    Account findByPasswordResetToken(@Param("token") String token);

}
