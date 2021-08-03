package io.programminglife.myfinances.service;

import java.util.List;

import io.programminglife.myfinances.entity.Expense;
import io.programminglife.myfinances.entity.csv.CsvEntity;
import io.programminglife.myfinances.entity.dashboard.Transaction;

public interface ExpenseService {

    List<Expense> findAll();

    Expense findExpenseById(Long expenseId);

    Expense saveExpense(CsvEntity csvEntity, Long clientId);

    void deleteExpense(Long expenseId, Long clientId);

    List<Expense> findExpenseByLabel(String label);

    List<Expense> findExpenseByCategory(Long categoryId);

    List<Expense> findExpenseByPaymentSystem(Long paymentSystemId);

    List<Transaction> findExpenseByClient(Long clientId);

    List<Transaction> findAllTransactions();
    
}