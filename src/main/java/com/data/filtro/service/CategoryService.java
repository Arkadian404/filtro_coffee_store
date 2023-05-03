package com.data.filtro.service;

import com.data.filtro.model.Category;
import com.data.filtro.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    public Page<Category> getAllPaging(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public void create(Category category) {
        categoryRepository.save(category);
    }

    public Category createCategory(Category category) {
        Category cate = categoryRepository.save(category);
        return cate;
    }

    public void update(Category category) {
        Category newCategory = getCategoryById(category.getId());
        newCategory.setCategoryName(category.getCategoryName());
        newCategory.setStatus(category.getStatus());
        categoryRepository.save(newCategory);
    }

    public Category updateCategory(int id, Category category) {
        System.out.println(category.getId() != null ? category.getId() : "null");
        Category newCategory = getCategoryById(id);
        if (newCategory != null) {
            newCategory.setCategoryName(category.getCategoryName());
            newCategory.setStatus(category.getStatus());
            categoryRepository.save(newCategory);
        }
        return newCategory;
    }


    @Transactional
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> get5Categories() {
        return categoryRepository.find5Categories();
    }

}
