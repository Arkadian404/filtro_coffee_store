package com.data.filtro.service;

import com.data.filtro.model.Flavor;
import com.data.filtro.repository.FlavorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlavorService {

    @Autowired
    FlavorRepository flavorRepository;

    public Flavor getFlavorById(int id) {
        return flavorRepository.findById(id).get();
    }

    public List<Flavor> getAll() {
        return flavorRepository.findAll();
    }
}
