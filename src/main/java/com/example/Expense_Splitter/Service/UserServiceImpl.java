package com.example.Expense_Splitter.Service;

import com.example.Expense_Splitter.DTOs.UserRequest;
import com.example.Expense_Splitter.DTOs.UserResponse;
import com.example.Expense_Splitter.Exception.ResourceNotFoundException;
import com.example.Expense_Splitter.Model.User;
import com.example.Expense_Splitter.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository _repo;
    private final ModelMapper _mapper;

    public UserServiceImpl(UserRepository repo, ModelMapper mapper) {
       _repo = repo;
        _mapper = mapper;
    }
    @Override
    public UserResponse createUser(UserRequest dto) {
        User user = _mapper.map(dto, User.class);
        User saved = _repo.save(user);
        return _mapper.map(saved, UserResponse.class);

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return _repo.findAll().stream()
                .map(user -> _mapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = _repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return _mapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse updateUser(Long id,UserRequest dto) {
        User user= _repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id));
       _mapper.map(dto,user);
        _repo.save(user);
        return _mapper.map(user,UserResponse.class);
    }

    @Override
    public UserResponse deleteUser(Long id) {
        User user=_repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id));
        _repo.delete(user);

        return _mapper.map(user,UserResponse.class);
    }
}
