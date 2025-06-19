package com.example.Expense_Splitter.config;

import com.example.Expense_Splitter.DTOs.ExpenseResponse;
import com.example.Expense_Splitter.Model.Expense;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper()
    {
//        ModelMapper mapper = new ModelMapper();
//
//
//        TypeMap<Expense, ExpenseResponse> typeMap = mapper.createTypeMap(Expense.class, ExpenseResponse.class);
//        typeMap.addMappings(m -> {
//            m.skip(ExpenseResponse::setPaidById);
//            m.skip(ExpenseResponse::setPaidByName);
//            m.skip(ExpenseResponse::setGroupId);
//            m.skip(ExpenseResponse::setGroupName);
//            m.skip(ExpenseResponse::setInvolvedUserIds);
//        });


        return new ModelMapper();
    }
}
