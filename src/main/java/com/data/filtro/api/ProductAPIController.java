package com.data.filtro.api;

import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.Product;
import com.data.filtro.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @GetMapping("/getbycate/{id}")
    public ResponseEntity<?> getProductListbycate(@PathVariable int id) {

        Pageable pageable = Pageable.unpaged();
        Page<Product> productList = productService.getProductByCategory(id, pageable);
        productList.forEach(s -> log.info(s.getProductName()));
        if (productList.isEmpty() || productList == null) {
            String message = "Không có danh sách cần tìm!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(productList.getContent(), HttpStatus.OK);
    }

    @GetMapping("/getbyflavor/{id}")
    public ResponseEntity<?> getProductListbyflavor(@PathVariable int id) {

        Pageable pageable = Pageable.unpaged();
        Page<Product> productList = productService.getProductsByFlavorId(id, pageable);
        productList.forEach(s -> log.info(s.getProductName()));
        if (productList.isEmpty() || productList == null) {
            String message = "Không có danh sách cần tìm!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productList.getContent(), HttpStatus.OK);
    }
}
