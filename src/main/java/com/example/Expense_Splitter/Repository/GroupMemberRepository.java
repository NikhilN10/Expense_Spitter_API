package com.example.Expense_Splitter.Repository;

import com.example.Expense_Splitter.Model.Group;
import com.example.Expense_Splitter.Model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember,Long> {
}
