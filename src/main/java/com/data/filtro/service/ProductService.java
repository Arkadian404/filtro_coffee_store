package com.data.filtro.service;

import com.data.filtro.model.Product;
import com.data.filtro.repository.ProductRepository;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ServletContext context;

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
        productRepository.save(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).get();
    }

    @Transactional
    public List<Product> getAll() {
        List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            Hibernate.initialize(product.getCategory());
        }
        return productList;
    }

    @Transactional
    public List<Product> getTopSellingProducts() {
        List<Product> productList = productRepository.findTopSellingProducts();
        for (Product product : productList) {
            Hibernate.initialize(product.getCategory());
        }
        return productList;
    }
}
