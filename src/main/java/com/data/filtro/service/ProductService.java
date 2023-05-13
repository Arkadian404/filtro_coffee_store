package com.data.filtro.service;

import com.data.filtro.model.Product;
import com.data.filtro.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;


    public void save(Product product) {
        productRepository.save(product);
    }


    public void addProduct(Product product, MultipartFile file) throws Exception {

        // Check if file is not empty and not null
        if (file != null && !file.isEmpty()) {
            // Get the original filename
            String originalFilename = file.getOriginalFilename();

            String projectDir = System.getProperty("user.dir");
            // Define the upload directory
            String uploadDir = projectDir + "\\src\\main\\upload\\product\\";
            System.out.println(uploadDir);
            // Create the upload path if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            if (file.getSize() > 5000000) { // Maximum file size of 5MB
                throw new Exception("File size too large. Please upload a file less than 5MB.");
            }
            // Create a new file with a unique name in the upload directory
            File uploadedFile = new File(uploadDir, UUID.randomUUID().toString() + "-" + originalFilename);
            // Save the uploaded file to disk
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, uploadedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            // Set the image field of the product to the path of the uploaded file
            product.setImage(uploadedFile.getName());
            System.out.println(uploadedFile);
        } else {
            product.setImage(null);
        }
        // Save the product to the database
        product.setCreatedDate(new Date());
        productRepository.save(product);
    }


    public void update(Product product, MultipartFile file) throws Exception {

        Product existingProduct = productRepository.findById(product.getId()).orElseThrow(() -> new Exception("Product not found"));

        // Update the existing product's properties with the new product's properties
        existingProduct.setProductName(product.getProductName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setSold(product.getSold());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setFlavor(product.getFlavor());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setStatus(product.getStatus());
        existingProduct.setDiscount(product.getDiscount());

        // Check if file is not empty and not null
        if (file != null && !file.isEmpty()) {
            // Get the original filename
            String originalFilename = file.getOriginalFilename();

            String projectDir = System.getProperty("user.dir");
            // Define the upload directory
            String uploadDir = projectDir + "\\src\\main\\upload\\product\\";
            System.out.println(uploadDir);
            // Create the upload path if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            if (file.getSize() > 5000000) { // Maximum file size of 5MB
                throw new Exception("File size too large. Please upload a file less than 5MB.");
            }
            // Create a new file with a unique name in the upload directory
            File uploadedFile = new File(uploadDir, UUID.randomUUID().toString() + "-" + originalFilename);
            // Save the uploaded file to disk
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, uploadedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            // Set the image field of the product to the path of the uploaded file
            existingProduct.setImage(uploadedFile.getName());
            System.out.println(uploadedFile);
        }
        // Save the product to the database
        productRepository.save(existingProduct);
    }


    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id);
    }

    @Transactional
    public List<Product> getAll() {
        List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            Hibernate.initialize(product.getCategory());
        }
        return productList;
    }

    public List<Product> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            String imgName = product.getImage();
            product.setImage("https://awesome-habitat-385806.as.r.appspot.com/upload/product/" + imgName);
        }
        return productList;
    }

    @Transactional
    public List<Product> getTopSellingProducts() {
        List<Product> productList = productRepository.findTop6SellingProducts();
        for (Product product : productList) {
            Hibernate.initialize(product.getCategory());
        }
        return productList;
    }

    public List<Product> getTopDiscountProducts() {
        List<Product> productList = productRepository.findTop3MostDiscountProducts();
        return productList;
    }


    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    public Page<Product> getProductByCategory(int id, Pageable pageable) {
        return productRepository.findProductsByCategory(id, pageable);
    }

    public Page<Product> getProductsByFlavorId(int id, Pageable pageable) {
        return productRepository.findProductsByFlavor(id, pageable);
    }


    public Page<Product> getProductsByName(String name, Pageable pageable) {
        return productRepository.findProducsByName(name, pageable);
    }

    public long countAll() {
        return productRepository.findAll().stream().count();
    }

    public List<Product> getTop4ProductsByFlavor(int id, int currentProductId) {
        return productRepository.findTop4ProductsByFlavor(id, currentProductId);
    }


}
