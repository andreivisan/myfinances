package io.programminglife.myfinances.util.dashboard;

import io.programminglife.myfinances.entity.Expense;
import io.programminglife.myfinances.entity.dashboard.Transaction;

public class DashboardUtil {

    public static Transaction expenseToTransaction(Expense expense) {
        Transaction transaction = new Transaction();

        transaction.setTransactionId(expense.getId());
        transaction.setDate(expense.getExpenseDate());
        transaction.setDescription(expense.getLabel());
        transaction.setAmount(expense.getAmount());
        transaction.setCategory(expense.getCategory().getLabel());
        transaction.setPaymentSystem(expense.getPaymentSystem().getLabel());

        return transaction;
    }
    
}