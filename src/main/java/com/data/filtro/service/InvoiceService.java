package com.data.filtro.service;

import com.data.filtro.model.*;
import com.data.filtro.repository.InvoiceDetailRepository;
import com.data.filtro.repository.InvoiceRepository;
import com.data.filtro.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    OrderRepository orderRepository;


    public List<Invoice> getAllInvoiceByUserId(int userId) {
        return invoiceRepository.findAllInvoiceByUserId(userId);
    }

    public Invoice getInvoiceByCartId(int cartId) {
        return invoiceRepository.findInvoiceByCartId(cartId);
    }


    public void makeInvoice(Order order) {
        Invoice invoice = new Invoice();
        invoice.setUser(order.getUser());
        invoice.setPurchasedDate(order.getOrderDate());
        invoiceRepository.save(invoice);

        List<OrderDetail> orderDetails = order.getOrderDetails();
        List<InvoiceDetail> invoiceDetails = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setInvoice(invoice);
            invoiceDetail.setProduct(orderDetail.getProduct());
            invoiceDetail.setQuantity(orderDetail.getQuantity());
            invoiceDetail.setPrice(orderDetail.getPrice());
            invoiceDetail.setTotal(orderDetail.getTotal());
            invoiceDetails.add(invoiceDetail);
            invoiceDetailRepository.save(invoiceDetail);
        }
        invoice.setTotal(order.getTotal());
        invoice.setInvoiceDetails(invoiceDetails);
        invoiceRepository.save(invoice);

        order.setStatus(2);
        orderRepository.save(order);
    }


    public Integer getRevenuePerMonthAndYear(int month, int year) {
        return invoiceRepository.findRevenuePerMonthAndYear(month, year);
    }

}
