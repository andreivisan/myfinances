package io.programminglife.myfinances.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.programminglife.myfinances.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(value = "SELECT * FROM expense WHERE label = :label", nativeQuery = true)
    List<Expense> findExpensesByLabel(@Param("label") String label);

    @Query(value = "SELECT * FROM expense WHERE category_id = :categoryId", nativeQuery = true)
    List<Expense> findExpenseByCategory(@Param("categoryId") Long categoryId);

    @Query(value = "SELECT * FROM expense WHERE payment_system_id = :paymentSystemId", nativeQuery = true)
    List<Expense> findExpenseByPaymentSystem(@Param("paymentSystemId") Long paymentSystemId);

    @Query(value = "SELECT SUM(amount) FROM expense WHERE category_id = :categoryId", nativeQuery = true)
    Long findTotalAmountByCategory(@Param("categoryId") Long categoryId);

    @Query(value = "SELECT * FROM expense WHERE client_id = :clientId", nativeQuery = true)
	List<Expense> findExpensesByClient(@Param("clientId") Long clientId);
    
}