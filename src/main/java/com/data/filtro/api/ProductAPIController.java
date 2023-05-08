package com.data.filtro.api;

import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.Product;
import com.data.filtro.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        List<Product> productList = productService.getAllProduct();
        productList.forEach(s -> log.info(s.getProductName()));
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> find(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            String message = "No product found!";
            ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
