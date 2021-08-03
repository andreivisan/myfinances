package io.programminglife.myfinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.programminglife.myfinances.entity.Category;
import io.programminglife.myfinances.entity.Client;
import io.programminglife.myfinances.entity.Expense;
import io.programminglife.myfinances.entity.PaymentSystem;
import io.programminglife.myfinances.entity.csv.CsvEntity;
import io.programminglife.myfinances.entity.dashboard.Transaction;
import io.programminglife.myfinances.exception.MyFinancesException;
import io.programminglife.myfinances.repository.ExpenseRepository;
import io.programminglife.myfinances.util.csv.CSVUtil;
import io.programminglife.myfinances.util.dashboard.DashboardUtil;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PaymentSystemService paymentSystemService;

    @Autowired
    private ClientService clientService;

    @Override
    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense findExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow(() -> new MyFinancesException(String.format("Expense with id %s could not be found", expenseId)));
    }

    @Override
    public Expense saveExpense(CsvEntity csvEntity, Long clientId) {
        Optional<Category> categoryOptional = categoryService.findCategoryByLabel(csvEntity.getCategory());
        Category category = categoryOptional.isPresent() ? categoryOptional.get()
            : categoryService.saveCategory(new Category(csvEntity.getCategory()));

        Optional<PaymentSystem> paymentSystemOptional = paymentSystemService.findPaymentSystemByLabel(csvEntity.getPaymentSystem());
        PaymentSystem paymentSystem = paymentSystemOptional.isPresent() ? paymentSystemOptional.get()
            : paymentSystemService.savePaymentSystem(new PaymentSystem(csvEntity.getPaymentSystem()));

        Client client = clientService.findClientById(clientId);
        Expense expense = CSVUtil.csvEntityToExpense(csvEntity, category, paymentSystem, client);
        List<Expense> clientExpenses = client.getExpenses();
        clientExpenses.add(expense);
        client.setExpenses(clientExpenses);
        Client savedClient = clientService.saveClient(client);

        return expenseRepository.save(CSVUtil.csvEntityToExpense(csvEntity, category, paymentSystem, savedClient));
    }

    @Override
    public void deleteExpense(Long expenseId, Long clientId) {
        Client client = clientService.findClientById(clientId);
        Expense expense = expenseRepository.getById(expenseId);
        List<Expense> clientExpenses = client.getExpenses();
        clientExpenses.remove(expense);
        client.setExpenses(clientExpenses);
        clientService.saveClient(client);

        expenseRepository.deleteById(expenseId);
    }

    @Override
    public List<Expense> findExpenseByLabel(String label) {
        return expenseRepository.findExpensesByLabel(label);
    }

    @Override
    public List<Expense> findExpenseByCategory(Long categoryId) {
        return expenseRepository.findExpenseByCategory(categoryId);
    }

    @Override
    public List<Expense> findExpenseByPaymentSystem(Long paymentSystemId) {
        return expenseRepository.findExpenseByPaymentSystem(paymentSystemId);
    }

    @Override
    public List<Transaction> findExpenseByClient(Long clientId) {
        return expenseRepository.findExpensesByClient(clientId).stream().map(expense -> {
            return DashboardUtil.expenseToTransaction(expense);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return findAll().stream().map(expense -> {
            return DashboardUtil.expenseToTransaction(expense);
        }).collect(Collectors.toList());
    }
    
}