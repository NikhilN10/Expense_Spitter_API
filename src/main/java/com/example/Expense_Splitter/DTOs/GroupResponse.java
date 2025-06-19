package com.example.Expense_Splitter.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private UserSummaryDTO createdBy;
    private List<UserSummaryDTO> members;


}
