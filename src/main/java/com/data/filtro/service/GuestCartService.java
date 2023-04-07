package com.data.filtro.service;

import com.data.filtro.model.GuestCart;
import com.data.filtro.repository.GuestCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestCartService {

    @Autowired
    GuestCartRepository guestCartRepository;


    public GuestCart getGuestCartById(int id) {
        return guestCartRepository.findGuestCartById(id);
    }

}
