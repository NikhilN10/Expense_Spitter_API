package com.example.Expense_Splitter.Controller;

import com.example.Expense_Splitter.DTOs.GroupRequest;
import com.example.Expense_Splitter.DTOs.GroupResponse;
import com.example.Expense_Splitter.Service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/group")
public class GroupController {

    private final GroupService _groupService;


    public GroupController(GroupService groupService) {
        _groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>>getAllGroups(){
        List<GroupResponse> groups=_groupService.getAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<GroupResponse>getGroup(@PathVariable Long Id){
        GroupResponse groupResponse=_groupService.getGroupById(Id);
        return new ResponseEntity<>(groupResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupResponse>createGroup(@Valid @RequestBody GroupRequest request){
        GroupResponse groupResponse=_groupService.createGroup(request);
                return new ResponseEntity<>(groupResponse,HttpStatus.CREATED);
    }
}
