package com.example.Expense_Splitter.Service;

import com.example.Expense_Splitter.DTOs.ExpenseRequest;
import com.example.Expense_Splitter.DTOs.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    ExpenseResponse create(ExpenseRequest request);
    List<ExpenseResponse>getAll();
    ExpenseResponse getExpenseById(Long Id);
}
