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

        User u = context.getBean(User.class);
        Product p = context.getBean(Product.class);
        Category c = context.getBean(Category.class);
        UserService uservice = context.getBean(UserService.class);
        ProductService uv = context.getBean(ProductService.class);
        CategoryService cs = context.getBean(CategoryService.class);
        List<Product> list = uv.getAll();
        List<User> uList =  uservice.getAll();
        List<Category> cList = cs.getAll();

        for(User user : uList){
            System.out.println(user.getName());
        }

        for(Product product : list){
            System.out.println(product.getProductName());
        }
        for(Category category : cList){
            System.out.println(category.getCategoryName());
        }


    }

}
