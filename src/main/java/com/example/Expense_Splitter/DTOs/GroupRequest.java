package com.example.Expense_Splitter.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {
    @NotBlank(message = "Group name is required")
    private String name;

    @NotNull(message = "Creator ID is required")
    private Long createdBy;

    private List<Long> memberIds;


}
