package com.example.Expense_Splitter.Repository;

import com.example.Expense_Splitter.DTOs.ExpenseResponse;
import com.example.Expense_Splitter.Model.Expense;
import com.example.Expense_Splitter.Model.Group;
import com.example.Expense_Splitter.Model.GroupMember;
import com.example.Expense_Splitter.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByGroup(Group group);

    List<Expense> findByPaidBy(User user);

   // List<Expense> findByPaidBy_Id(Long userId);

    // ➋  All expenses where the user appears in splits
    List<Expense> findBySplits_User_Id(Long userId);

    Page<Expense> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
}
