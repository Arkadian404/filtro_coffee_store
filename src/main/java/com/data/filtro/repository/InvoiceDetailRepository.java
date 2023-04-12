package com.data.filtro.repository;

import com.data.filtro.model.Invoice;
import com.data.filtro.model.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {
}
