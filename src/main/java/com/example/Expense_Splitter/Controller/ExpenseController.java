package com.example.Expense_Splitter.Controller;

import com.example.Expense_Splitter.DTOs.ExpenseRequest;
import com.example.Expense_Splitter.DTOs.ExpenseResponse;
import com.example.Expense_Splitter.Service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseService _expenseService;


    public ExpenseController(ExpenseService expenseService) {
        _expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAll(){

        List<ExpenseResponse> expenseList=_expenseService.getAll();
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

}
