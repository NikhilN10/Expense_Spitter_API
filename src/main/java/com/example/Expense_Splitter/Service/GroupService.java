package com.example.Expense_Splitter.Service;

import com.example.Expense_Splitter.DTOs.GroupRequest;
import com.example.Expense_Splitter.DTOs.GroupResponse;

import java.util.List;

public interface GroupService {
List<GroupResponse> getAllGroups();
GroupResponse getGroupById(Long id);
GroupResponse createGroup(GroupRequest request);
}
