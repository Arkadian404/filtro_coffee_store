package com.data.filtro.service;

import com.data.filtro.model.Invoice;
import com.data.filtro.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;


    public List<Invoice> getAllInvoiceByUserId(int userId) {
        return invoiceRepository.findAllInvoiceByUserId(userId);
    }

    public Invoice getInvoiceByCartId(int cartId) {
        return invoiceRepository.findInvoiceByCartId(cartId);
    }

}
