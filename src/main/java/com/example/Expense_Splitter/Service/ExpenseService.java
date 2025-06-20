package com.example.Expense_Splitter.Service;

import com.example.Expense_Splitter.DTOs.ExpenseRequest;
import com.example.Expense_Splitter.DTOs.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    ExpenseResponse create(ExpenseRequest request);
    List<ExpenseResponse>getAll();
    ExpenseResponse getExpenseById(Long Id);
    ExpenseResponse updateExpense(ExpenseRequest request,Long id);
    void deleteExpense(Long id);
    List<ExpenseResponse>getExpensesByGroupId(Long id);
    List<ExpenseResponse>getExpensesByPaidByUser(Long id);
}
