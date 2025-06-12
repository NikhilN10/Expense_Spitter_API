package com.example.Expense_Splitter.DTOs;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class GroupResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private UserSummaryDTO createdBy;
    private List<UserSummaryDTO> members;

    public GroupResponse(){}

    public GroupResponse(Long id, String name, LocalDateTime createdAt, UserSummaryDTO createdBy, List<UserSummaryDTO> members) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.members = members;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<UserSummaryDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserSummaryDTO> members) {
        this.members = members;
    }

    public UserSummaryDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserSummaryDTO createdBy) {
        this.createdBy = createdBy;
    }
}
