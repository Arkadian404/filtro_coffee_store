package com.data.filtro.service;

import com.data.filtro.model.Category;
import com.data.filtro.model.Product;
import com.data.filtro.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(int id){
        return productRepository.findById(id).get();
    }

    @Transactional
    public List<Product> getAll(){
        return productRepository.findAll();
    }
}
