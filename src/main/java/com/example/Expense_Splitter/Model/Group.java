package com.example.Expense_Splitter.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;//It's a group name

    @ManyToOne //Many groups can be created by one user
    @JoinColumn(name = "created_by")
    private User createdBy;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "group")
    List<GroupMember>members= new ArrayList<>();


    @OneToMany(mappedBy = "group")
    private List<Expense> expenses;


}
