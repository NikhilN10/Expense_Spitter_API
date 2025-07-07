package com.example.Expense_Splitter.Service;

import com.example.Expense_Splitter.DTOs.ExpenseRequest;
import com.example.Expense_Splitter.DTOs.ExpenseResponse;
import com.example.Expense_Splitter.DTOs.GroupBalanceDTO;
import com.example.Expense_Splitter.DTOs.PaginatedResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ExpenseService {

    ExpenseResponse create(ExpenseRequest request);
    PaginatedResponse<ExpenseResponse> getAll(int page, int size, String description);
    ExpenseResponse getExpenseById(Long Id);
    ExpenseResponse updateExpense(ExpenseRequest request,Long id);
    void deleteExpense(Long id);
    List<ExpenseResponse>getExpensesByGroupId(Long id);
    List<ExpenseResponse>getExpensesByPaidByUser(Long id);

    Map<String, BigDecimal> calculateUserBalance(Long userId);

    List<GroupBalanceDTO> calculateGroupBalance(Long groupId);

    List<ExpenseResponse> getUserTransactions(Long userId);
}
