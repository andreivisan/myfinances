package io.programminglife.myfinances.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.programminglife.myfinances.entity.Expense;
import io.programminglife.myfinances.entity.csv.CsvEntity;
import io.programminglife.myfinances.entity.dashboard.Transaction;
import io.programminglife.myfinances.exception.MyFinancesException;
import io.programminglife.myfinances.security.CurrentUser;
import io.programminglife.myfinances.security.UserPrincipal;
import io.programminglife.myfinances.service.ExpenseService;

@RestController
@RequestMapping("api/v1/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/save")
    public ResponseEntity<Expense> save(@RequestBody CsvEntity csvEntity, @CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok().body(expenseService.saveExpense(csvEntity, currentUser.getId()));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> findAll() {
        return ResponseEntity.ok().body(expenseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findExpenseById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok().body(expenseService.findExpenseById(id));
        } catch (MyFinancesException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        expenseService.deleteExpense(id, currentUser.getId());
    }

    @GetMapping("/label/{label}")
    public List<Expense> findExpensesByLabelEquals(@PathVariable(value = "label") String label) {
        return expenseService.findExpenseByLabel(label);
    }

    @GetMapping("/category/{categoryId}")
    public List<Expense> findExpensesByCategory(@PathVariable(value = "categoryId") Long categoryId) {
        return expenseService.findExpenseByCategory(categoryId);
    }

    @GetMapping("/paymentsystem/{paymentSystemId}")
    public List<Expense> findExpensesByPaymentSystem(
            @PathVariable(value = "paymentSystemId") Long paymentSystemId) {
        return expenseService.findExpenseByPaymentSystem(paymentSystemId);
    }

    @GetMapping("/toTransactions")
    public ResponseEntity<List<Transaction>> findAllTransactions() {
        return ResponseEntity.ok().body(expenseService.findAllTransactions());
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> findAllTransactionsForCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok().body(expenseService.findExpenseByClient(currentUser.getId()));
    }
    
}