package io.programminglife.myfinances.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.programminglife.myfinances.entity.Category;
import io.programminglife.myfinances.entity.Expense;
import io.programminglife.myfinances.exception.MyFinancesException;
import io.programminglife.myfinances.repository.CategoryRepository;
import io.programminglife.myfinances.repository.ExpenseRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Long categoryId) throws MyFinancesException {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new MyFinancesException(String.format("Category with id %s was not found", categoryId)));
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Optional<Category> findCategoryByLabel(String label) {
        return categoryRepository.findCategoryByLabel(label);
    }

    @Override
    public Map<String, Float> findTotalAmountPerCategory(Long clientId) {
        Map<String, Float> totalMonthlyAmountPerCategory = new HashMap<>();
        List<Expense> clientExpenses = expenseRepository.findExpensesByClient(clientId);

        for(Expense expense : clientExpenses) {
            Category category = expense.getCategory();
            Float expenseAmount = expense.getAmount();

            totalMonthlyAmountPerCategory.put(category.getLabel(), 
                totalMonthlyAmountPerCategory.getOrDefault(category.getLabel(), 0f) + expenseAmount);
        }

        return totalMonthlyAmountPerCategory.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
    
}