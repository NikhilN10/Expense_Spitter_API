package com.example.Expense_Splitter.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSplitDTO {
    private Long userId;
    private String userName;
    private BigDecimal amountOwed;
}
