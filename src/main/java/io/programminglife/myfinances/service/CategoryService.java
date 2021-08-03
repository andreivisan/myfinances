package io.programminglife.myfinances.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.programminglife.myfinances.entity.Category;
import io.programminglife.myfinances.exception.MyFinancesException;

public interface CategoryService {

    List<Category> findAll();

    Category findCategoryById(Long categoryId) throws MyFinancesException;

    Category saveCategory(Category category);

    void deleteCategory(Long categoryId);

    Optional<Category> findCategoryByLabel(String label);

    Map<String, Float> findTotalAmountPerCategory(Long clientId);
    
}