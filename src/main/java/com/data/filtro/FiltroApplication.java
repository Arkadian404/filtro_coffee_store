package com.data.filtro;

import com.data.filtro.config.DatabaseConnection;
import com.data.filtro.model.Category;
import com.data.filtro.model.Product;
import com.data.filtro.model.User;
import com.data.filtro.repository.UserRepository;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.ProductService;
import com.data.filtro.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@SpringBootApplication
public class FiltroApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FiltroApplication.class, args);




    }

}
