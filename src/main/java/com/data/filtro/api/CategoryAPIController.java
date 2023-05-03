package com.data.filtro.api;

import com.data.filtro.model.Category;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Category category) {
        if (category != null) {
            Category cate = categoryService.createCategory(category);
            return new ResponseEntity<>(cate, HttpStatus.OK);
        } else {
            String message = "Can't be null";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Category category) {
        Category newCategory = categoryService.getCategoryById(id);
        if (newCategory != null) {
            newCategory = categoryService.updateCategory(id, category);
            return new ResponseEntity<>(newCategory, HttpStatus.OK);
        } else {
            String message = "Can't be null";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Category cate = categoryService.getCategoryById(id);
        if (cate != null) {
            categoryService.delete(id);
            return new ResponseEntity<>(cate, HttpStatus.OK);
        } else {
            String message = "Can't be null";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
