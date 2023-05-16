package com.data.filtro.service;

import com.data.filtro.model.Account;
import com.data.filtro.model.Order;
import com.data.filtro.model.Shipper;
import com.data.filtro.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShipperService {

    @Autowired
    ShipperRepository shipperRepository;

    @Autowired
    OrderService orderService;


    public Shipper getById(int id) {
        return shipperRepository.findById(id);
    }

    public List<Shipper> getAll() {
        return shipperRepository.findAll();
    }

    public Shipper getByOrderId(int id) {
        return shipperRepository.findByOrderId(id);
    }


    public void shipping(Account account, Order order) {
        Shipper shipper = new Shipper();
        if (order != null) {
            shipper.setAccount(account);
            shipper.setOrder(order);
            shipper.setShippedDate(new Date());
            shipper.setDeliveredDate(null);
            shipper.setStatus(1);
            shipperRepository.save(shipper);
            orderService.updateOrderShippingStatus(order.getId());
        }

    }

    public void delivered(int orderId) {
        Shipper shipper = getByOrderId(orderId);
        if (shipper != null) {
            shipper.setDeliveredDate(new Date());
            shipper.setStatus(2);
            shipperRepository.save(shipper);
            orderService.updateOrderDeliveredStatus(orderId);
        }
    }

}
