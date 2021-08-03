package io.programminglife.myfinances.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.programminglife.myfinances.entity.Category;
import io.programminglife.myfinances.security.CurrentUser;
import io.programminglife.myfinances.security.UserPrincipal;
import io.programminglife.myfinances.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<Category> save(@RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.saveCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok().body(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok().body(categoryService.findCategoryById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/totalMonthlyAmountPerCategory")
    public ResponseEntity<Map<String, Float>> findTotalMonthlyAmountPerCategory(
            @CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok().body(categoryService.findTotalAmountPerCategory(currentUser.getId()));
    }
    
}