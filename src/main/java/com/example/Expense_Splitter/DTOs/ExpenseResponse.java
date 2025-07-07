package com.example.Expense_Splitter.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
    private Long paidById;
    private String paidByName;
    private Long groupId;
    private String groupName;
    private List<Long> involvedUserIds;
    private LocalDateTime createdAt;

    private List<ExpenseSplitDTO> splits;



}
