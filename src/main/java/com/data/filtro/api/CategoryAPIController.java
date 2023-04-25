package com.data.filtro.api;

import com.data.filtro.model.Category;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryAPIController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> find(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            String message = "No category found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Category> categories = categoryService.getAll();
        if (categories == null || categories.isEmpty()) {
            String message = "No categories found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
