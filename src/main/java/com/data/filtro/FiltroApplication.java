package com.data.filtro;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FiltroApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FiltroApplication.class, args);
        //SpringApplication.run(FiltroApplication.class, args);

//
//        Category category = context.getBean(Category.class);
//        Product AdminProductController = context.getBean(Product.class);
//
//        ProductService productService = context.getBean(ProductService.class);
//
//        List<Product> productList = new ArrayList<>();
//        productList = productService.getTopSellingProducts();
//
//        productList.stream().forEach(p -> System.out.println(p.getProductName() + "\n" + p.getCategory().getCategoryName()));

//        String dir = System.getProperty("user.dir");
//        System.out.println(dir);
    }

}
