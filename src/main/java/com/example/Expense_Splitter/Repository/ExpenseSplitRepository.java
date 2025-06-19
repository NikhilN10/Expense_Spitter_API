package com.example.Expense_Splitter.Repository;

import com.example.Expense_Splitter.Model.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {
}
