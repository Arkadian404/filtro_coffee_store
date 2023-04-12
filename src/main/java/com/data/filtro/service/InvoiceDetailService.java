package com.data.filtro.service;

import com.data.filtro.model.InvoiceDetail;
import com.data.filtro.repository.InvoiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceDetailService {

    @Autowired
    InvoiceDetailRepository invoiceDetailRepository;

    
}
