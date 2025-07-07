package com.example.Expense_Splitter.Service;

import com.example.Expense_Splitter.DTOs.*;
import com.example.Expense_Splitter.Exception.ResourceNotFoundException;
import com.example.Expense_Splitter.Model.*;
import com.example.Expense_Splitter.Repository.ExpenseRepository;
import com.example.Expense_Splitter.Repository.GroupRepository;
import com.example.Expense_Splitter.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
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
    public PaginatedResponse<ExpenseResponse> getAll(int page,int size,String description) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Expense> expensePage;
        if(description!=null || !description.isBlank()){
            expensePage=_expenseRepo.findByDescriptionContainingIgnoreCase(description,pageable);
        }
        else{
            expensePage=_expenseRepo.findAll(pageable);
        }
        List<ExpenseResponse>content=expensePage.getContent().stream()
                .map(expense -> convertToResponse(expense,null))
                .toList();
      return  new PaginatedResponse<ExpenseResponse>(
              content,
              expensePage.getNumber(),
              expensePage.getSize(),
              expensePage.getTotalElements(),
              expensePage.getTotalPages(),
              expensePage.isLast()
      );
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

    @Override
    public Map<String, BigDecimal> calculateUserBalance(Long userId) {
        User user=_userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        List<Expense>expenses=_expenseRepo.findAll();

        BigDecimal paid= BigDecimal.ZERO;
        BigDecimal owed= BigDecimal.ZERO;

        for(Expense e:expenses){
            for(ExpenseSplit split:e.getSplits()){
                if(split.getUser().getId().equals(userId)){
                    owed=owed.add(split.getAmount());
                }
            }
            if(e.getPaidBy().getId().equals(userId)){
            paid=paid.add(e.getAmount());}
        }
        Map<String,BigDecimal>result=new HashMap<>();
        result.put("Paid",paid);
        result.put("Owed",owed);
        result.put("Net Balance",paid.subtract(owed));

        return result;

    }

    @Override
    public List<GroupBalanceDTO> calculateGroupBalance(Long groupId) {
        Group group=_groupRepo.findById(groupId).orElseThrow(()-> new ResourceNotFoundException("Group not found"));
        List<User>groupMembers=group.getMembers().stream().map(gr->gr.getUser()).toList();
        List<Expense>groupExpense=_expenseRepo.findByGroup(group);
        Map<Long,BigDecimal>paidMap=new HashMap<>();
        Map<Long,BigDecimal>owedMap=new HashMap<>();

        for(Expense e:groupExpense){
            Long payerId=e.getPaidBy().getId();
            paidMap.put(payerId,paidMap.getOrDefault(payerId,BigDecimal.ZERO).add(e.getAmount()));
        for(ExpenseSplit split:e.getSplits()){
            Long uid=split.getUser().getId();
            owedMap.put(uid,owedMap.getOrDefault(uid,BigDecimal.ZERO).add(split.getAmount()));
        }
        }
        List<GroupBalanceDTO>result=new ArrayList<>();
        for(User u:groupMembers) {
            BigDecimal paid = paidMap.getOrDefault(u.getId(),BigDecimal.ZERO);
            BigDecimal owed = owedMap.getOrDefault(u.getId(),BigDecimal.ZERO);
            BigDecimal net=paid.subtract(owed);

            GroupBalanceDTO dto= new GroupBalanceDTO(u.getId(),u.getName(),paid,owed,net);
            result.add(dto);
        }
        return result;

    }

    @Override
    public List<ExpenseResponse> getUserTransactions(Long userId) {
      User user= _userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(" User Not Found"));

      List<Expense>paidList= _expenseRepo.findByPaidBy(user);
        List<Expense> involvedList = _expenseRepo.findBySplits_User_Id(userId);

        // Use a Set to avoid duplicates when user both paid & participated
        Set<Expense> union = new LinkedHashSet<>();
        union.addAll(paidList);
        union.addAll(involvedList);

        return union.stream().map(e->convertToResponse(e,null)).toList();
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
