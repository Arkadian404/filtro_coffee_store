package com.data.filtro.api;

import com.data.filtro.model.Product;
import com.data.filtro.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductAPIController {


    @Autowired
    ProductService productService;

    @GetMapping("/getProductList")
    public ResponseEntity<List<Product>> getProductList() {

        List<Product> productList = productService.getAll();
        productList.forEach(s -> log.info(s.getProductName()));
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}
