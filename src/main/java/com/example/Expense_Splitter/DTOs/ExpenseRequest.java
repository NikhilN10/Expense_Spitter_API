package com.example.Expense_Splitter.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {

    // Optional: You can allow setting custom time or default to now in service
    private LocalDateTime createdAt;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Payer ID is required")
    private Long paidBy;

    @NotNull(message = "Group ID is required")
    private Long groupId;

    @NotEmpty(message = "At least one member must be involved")
    private List<Long> involvedUserIds;


}
