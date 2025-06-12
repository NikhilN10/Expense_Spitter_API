package com.example.Expense_Splitter.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

public class GroupRequest {
    @NotBlank(message = "Group name is required")
    private String name;

    @NotNull(message = "Creator ID is required")
    private Long createdBy;

    private List<Long> memberIds;

    public GroupRequest() {
    }

    public GroupRequest(String name, Long createdBy, List<Long> memberIds) {
        this.name = name;
        this.createdBy = createdBy;
        this.memberIds = memberIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
}
