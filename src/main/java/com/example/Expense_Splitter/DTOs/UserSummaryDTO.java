package com.example.Expense_Splitter.DTOs;

public class UserSummaryDTO {
    private Long id;
    private String name;

    public UserSummaryDTO() {
    }

    public UserSummaryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
