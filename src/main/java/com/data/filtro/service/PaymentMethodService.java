package com.data.filtro.service;

import com.data.filtro.model.PaymentMethod;
import com.data.filtro.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {
    @Autowired
    PaymentMethodRepository paymentMethodRepository;


    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod getPaymentMethodById(int id) {
        return paymentMethodRepository.findPaymentMethodById(id);
    }
}
