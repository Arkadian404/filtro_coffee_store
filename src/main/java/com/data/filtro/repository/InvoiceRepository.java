package com.data.filtro.repository;

import com.data.filtro.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("select i from Invoice i where i.user.id =:userId")
    List<Invoice> findAllInvoiceByUserId(@Param("userId") int userId);

    @Query("select i from Invoice i where i.user.cart.id =:cartId")
    Invoice findInvoiceByCartId(@Param("cartId") int cartId);

}
