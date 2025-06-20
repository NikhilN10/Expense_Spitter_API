package com.example.Expense_Splitter.Service;

import com.example.Expense_Splitter.DTOs.ExpenseRequest;
import com.example.Expense_Splitter.DTOs.ExpenseResponse;
import com.example.Expense_Splitter.DTOs.ExpenseSplitDTO;
import com.example.Expense_Splitter.Exception.ResourceNotFoundException;
import com.example.Expense_Splitter.ExpenseSplitterApplication;
import com.example.Expense_Splitter.Model.Expense;
import com.example.Expense_Splitter.Model.ExpenseSplit;
import com.example.Expense_Splitter.Model.Group;
import com.example.Expense_Splitter.Model.User;
import com.example.Expense_Splitter.Repository.ExpenseRepository;
import com.example.Expense_Splitter.Repository.GroupRepository;
import com.example.Expense_Splitter.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService{
    private final UserRepository _userRepo;
    private final GroupRepository _groupRepo;
    private final ExpenseRepository _expenseRepo;
    private  final ModelMapper _mapper;

    public ExpenseServiceImpl(UserRepository userRepo, GroupRepository groupRepo, ExpenseRepository expenseRepo, ModelMapper mapper) {
        _userRepo = userRepo;
        _groupRepo = groupRepo;
        _expenseRepo = expenseRepo;
        _mapper = mapper;
    }

    @Override
    public ExpenseResponse create(ExpenseRequest request) {
      User paidBy= _userRepo.findById(request.getPaidBy()).orElseThrow(() -> new ResourceNotFoundException("Payer not found"));
      Group group= _groupRepo.findById(request.getGroupId()).orElseThrow(()->new ResourceNotFoundException("Group not found"));

      Expense expense=new Expense();
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setGroup(group);
        expense.setPaidBy(paidBy);
        expense.setCreatedAt(LocalDateTime.now());
       // Expense saved = _expenseRepo.save(expense);

        // 💰 Calculate split amount
        BigDecimal totalAmount = request.getAmount();
        int numberOfUsers = request.getInvolvedUserIds().size();
        BigDecimal splitAmount = totalAmount.divide(BigDecimal.valueOf(numberOfUsers), 2, RoundingMode.HALF_UP);

        List<ExpenseSplit> splits = new ArrayList<>();

        for (Long userId : request.getInvolvedUserIds()) {
            User user = _userRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User ID " + userId + " not found"));

            ExpenseSplit split = new ExpenseSplit();
            split.setExpense(expense);
            split.setUser(user);
            split.setAmount(splitAmount);

            splits.add(split);
        }

        expense.setSplits(splits);
        Expense saved=_expenseRepo.save(expense);// Set to parent for bidirectional
        return convertToResponse(saved,request.getInvolvedUserIds());
    }

    @Override
    public List<ExpenseResponse> getAll() {
      return  _expenseRepo.findAll().stream().map(e->convertToResponse(e,null)).toList();
    }

    @Override
    public ExpenseResponse getExpenseById(Long Id) {
        Expense expense=_expenseRepo.findById(Id).orElseThrow(()->new ResourceNotFoundException("Expense not found"));
        return convertToResponse(expense,null);
    }

    @Override
    public ExpenseResponse updateExpense(ExpenseRequest request, Long id) {
        Expense existing= _expenseRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Expense not found"));

        User paidBy=_userRepo.findById(request.getPaidBy()).orElseThrow(()-> new ResourceNotFoundException("Payer not found"));

        Group group=_groupRepo.findById(request.getGroupId()).orElseThrow(()-> new ResourceNotFoundException("Group not found"));

        List<User>involved= _userRepo.findAllById(request.getInvolvedUserIds());
        BigDecimal splitAmount=request.getAmount().divide(BigDecimal.valueOf(involved.size()),2,RoundingMode.HALF_UP);
        existing.setDescription(request.getDescription());
       // existing.setCreatedAt(request.getCreatedAt());
        existing.setAmount(request.getAmount());
        existing.setGroup(group);
        existing.setPaidBy(paidBy);

        List<ExpenseSplit>newSplit= involved.stream().map(user ->{
            ExpenseSplit split=new ExpenseSplit();
            split.setExpense(existing);
            split.setUser(user);
            split.setAmount(splitAmount);
            return split;
        }).toList();
        existing.getSplits().clear();
        existing.getSplits().addAll(newSplit);

        Expense updated=_expenseRepo.save(existing);
      return convertToResponse(updated,request.getInvolvedUserIds());


    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense=_expenseRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Expense not found"));
        _expenseRepo.delete(expense);
    }

    @Override
    public List<ExpenseResponse> getExpensesByGroupId(Long id) {
        Group group=_groupRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Group not found"));
        List<Expense> expenseList= _expenseRepo.findByGroup(group);

        return expenseList.stream().map(ex->convertToResponse(ex,null)).toList();

    }

    @Override
    public List<ExpenseResponse> getExpensesByPaidByUser(Long id) {
        User paidBy= _userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payer not found"));

       List<Expense>expenseList=_expenseRepo.findByPaidBy(paidBy);
        return expenseList.stream().map(ex->convertToResponse(ex,null)).toList();
    }

    private ExpenseResponse convertToResponse(Expense e, List<Long> involved) {
        ExpenseResponse res = _mapper.map(e, ExpenseResponse.class);
        res.setPaidById(e.getPaidBy().getId());
        res.setPaidByName(e.getPaidBy().getName());
        res.setCreatedAt(e.getCreatedAt());
        res.setGroupId(e.getGroup().getId());
        res.setGroupName(e.getGroup().getName());
       // res.setInvolvedUserIds(involved != null ? involved : new ArrayList<>());
        if (involved != null) {
            res.setInvolvedUserIds(involved);
        } else {
            List<Long> ids = e.getSplits().stream()
                    .map(split -> split.getUser().getId())
                    .collect(Collectors.toList());
            res.setInvolvedUserIds(ids);
        }


        // Map splits
        List<ExpenseSplitDTO> splitDTOs = e.getSplits().stream().map(split -> {
            ExpenseSplitDTO dto = new ExpenseSplitDTO();
            dto.setUserId(split.getUser().getId());
            dto.setUserName(split.getUser().getName());
            dto.setAmountOwed(split.getAmount());
            return dto;
        }).toList();
        res.setSplits(splitDTOs);
        return res;
    }
}
