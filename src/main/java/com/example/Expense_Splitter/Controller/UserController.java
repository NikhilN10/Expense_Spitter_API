package com.example.Expense_Splitter.Controller;

import com.example.Expense_Splitter.DTOs.UserRequest;
import com.example.Expense_Splitter.DTOs.UserResponse;
import com.example.Expense_Splitter.Model.User;
import com.example.Expense_Splitter.Service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@Validated
public class UserController {

    private  final UserService _userService;

    public UserController(UserService userService) {
        _userService = userService;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> GetUsers(){
        List<UserResponse> users= _userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("/getUser/{Id}")
    public ResponseEntity<UserResponse> GetUserById(@PathVariable Long Id){
        UserResponse user= _userService.getUserById(Id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest dto) {
        UserResponse created = _userService.createUser(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{Id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long Id,@Valid @RequestBody UserRequest Dto){
        UserResponse user= _userService.updateUser(Id,Dto);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long Id){
        UserResponse user= _userService.deleteUser(Id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
