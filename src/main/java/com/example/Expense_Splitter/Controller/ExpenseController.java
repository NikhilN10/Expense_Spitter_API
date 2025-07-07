package com.example.Expense_Splitter.Controller;

import com.example.Expense_Splitter.DTOs.ExpenseRequest;
import com.example.Expense_Splitter.DTOs.ExpenseResponse;
import com.example.Expense_Splitter.DTOs.GroupBalanceDTO;
import com.example.Expense_Splitter.DTOs.PaginatedResponse;
import com.example.Expense_Splitter.Service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseService _expenseService;


    public ExpenseController(ExpenseService expenseService) {
        _expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ExpenseResponse>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size,
                                                                     @RequestParam(required = false) String description){

        PaginatedResponse<ExpenseResponse> expenseList=_expenseService.getAll(page,size,description);
        return new ResponseEntity<>(expenseList,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> create(@Valid @RequestBody ExpenseRequest req) {
        return new ResponseEntity<>(_expenseService.create(req), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getById(@PathVariable Long id) {
        ExpenseResponse e = _expenseService.getExpenseById(id);
        return new ResponseEntity<>(e,HttpStatus.OK);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<ExpenseResponse>> getExpenseByGroupId(@PathVariable Long id) {
        List<ExpenseResponse> e = _expenseService.getExpensesByGroupId(id);
        return new ResponseEntity<>(e,HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ExpenseResponse>> getExpenseByPaidByUser(@PathVariable Long id) {
        List<ExpenseResponse> e = _expenseService.getExpensesByPaidByUser(id);
        return new ResponseEntity<>(e,HttpStatus.OK);
    }
    @GetMapping("user/{userId}/balance")
    public ResponseEntity<Map<String, BigDecimal>>getUserBalance(@PathVariable Long userId){
        Map<String,BigDecimal> balance=_expenseService.calculateUserBalance(userId);
        return new ResponseEntity<>(balance,HttpStatus.OK);

    }
    @GetMapping("group/{groupId}/balance")
    public ResponseEntity<List<GroupBalanceDTO>>getGroupBalance(@PathVariable Long groupId){
        List<GroupBalanceDTO> balance=_expenseService.calculateGroupBalance(groupId);
        return new ResponseEntity<>(balance,HttpStatus.OK);

    }
    @GetMapping("/user/{userId}/transactions")
    public ResponseEntity<List<ExpenseResponse>>getUserTransactions(@PathVariable Long userId){
        List<ExpenseResponse>e= _expenseService.getUserTransactions(userId);

        return  new ResponseEntity<>(e,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(@Valid @RequestBody ExpenseRequest request,@PathVariable Long id){
        ExpenseResponse updatedExpense= _expenseService.updateExpense(request,id);
        return  new ResponseEntity<>(updatedExpense,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteteExpense(@PathVariable Long id){
        _expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }


}
