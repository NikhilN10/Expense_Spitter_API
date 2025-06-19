package com.example.Expense_Splitter.Repository;

import com.example.Expense_Splitter.Model.Expense;
import com.example.Expense_Splitter.Model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
}
