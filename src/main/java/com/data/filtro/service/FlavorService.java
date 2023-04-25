package com.data.filtro.service;

import com.data.filtro.model.Flavor;
import com.data.filtro.repository.FlavorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlavorService {

    @Autowired
    FlavorRepository flavorRepository;

    public Flavor getFlavorById(int id) {
        return flavorRepository.findById(id);
    }

    public Page<Flavor> getAllPaging(Pageable pageable) {
        return flavorRepository.findAll(pageable);
    }

    public void create(Flavor flavor) {
        flavorRepository.save(flavor);
    }


    public void update(Flavor flavor) {
        Flavor newFlavor = getFlavorById(flavor.getId());
        newFlavor.setFlavorName(flavor.getFlavorName());
        newFlavor.setDescription(flavor.getDescription());
        newFlavor.setStatus(flavor.getStatus());
        flavorRepository.save(newFlavor);
    }

    @Transactional
    public void delete(int id) {
        flavorRepository.deleteById(id);
    }

    public List<Flavor> getAll() {
        return flavorRepository.findAll();
    }
}
