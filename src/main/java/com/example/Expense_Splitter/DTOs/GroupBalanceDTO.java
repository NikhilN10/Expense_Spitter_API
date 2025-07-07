package com.example.Expense_Splitter.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupBalanceDTO {
    private Long userId;
    private String userName;
    private BigDecimal paid;
    private BigDecimal owed;
    private BigDecimal netBalance;
}

