package com.example.Expense_Splitter.Service;

import com.example.Expense_Splitter.DTOs.UserRequest;
import com.example.Expense_Splitter.DTOs.UserResponse;
import com.example.Expense_Splitter.Model.User;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest dto);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id,UserRequest dto);

    UserResponse deleteUser(Long id);
}

