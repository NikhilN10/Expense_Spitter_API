package com.example.Expense_Splitter.Service;

import com.example.Expense_Splitter.DTOs.GroupRequest;
import com.example.Expense_Splitter.DTOs.GroupResponse;
import com.example.Expense_Splitter.DTOs.UserSummaryDTO;
import com.example.Expense_Splitter.Exception.ResourceNotFoundException;
import com.example.Expense_Splitter.Model.Group;
import com.example.Expense_Splitter.Model.GroupMember;
import com.example.Expense_Splitter.Model.User;
import com.example.Expense_Splitter.Repository.GroupMemberRepository;
import com.example.Expense_Splitter.Repository.GroupRepository;
import com.example.Expense_Splitter.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService{

    private final GroupRepository _repo;
    private final UserRepository _userRepo;
    private final GroupMemberRepository _groupMemberRepo;
    private final ModelMapper _mapper;

    public GroupServiceImpl(GroupRepository repo, UserRepository userRepo, GroupMemberRepository groupMemberRepo, ModelMapper mapper) {
        _repo = repo;
        _userRepo = userRepo;
        _groupMemberRepo = groupMemberRepo;
        _mapper = mapper;
    }

    @Override
    public List<GroupResponse> getAllGroups() {
      List<Group>groups= _repo.findAll();
     return groups.stream().map(g->convertToResponse(g)).toList();
    }

    @Override
    public GroupResponse getGroupById(Long id) {
       Group group=_repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id));
       return _mapper.map(group, GroupResponse.class);
    }

    @Override
    public GroupResponse createGroup(GroupRequest request) {
        User creator = _userRepo.findById(request.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getCreatedBy()));

        Group group = new Group();
        group.setName(request.getName());
        group.setCreatedBy(creator);
        group.setCreatedAt(LocalDateTime.now());

        Group saved=_repo.save(group);

        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            List<User> members = _userRepo.findAllById(request.getMemberIds());
            List<GroupMember>groupMembers=new ArrayList<>();
            for (User user : members) {
                GroupMember gm = new GroupMember();
                gm.setGroup(saved);
                gm.setUser(user);
               groupMembers.add(_groupMemberRepo.save(gm));
            }
            saved.setMembers(groupMembers);
        }
                return convertToResponse(saved);
    }

        private GroupResponse convertToResponse(Group group) {
            GroupResponse dto = _mapper.map(group, GroupResponse.class);
            dto.setCreatedBy(_mapper.map(group.getCreatedBy(), UserSummaryDTO.class));

            List<UserSummaryDTO> memberDTOs = group.getMembers().stream()
                    .map(member -> _mapper.map(member.getUser(), UserSummaryDTO.class))
                    .collect(Collectors.toList());

            dto.setMembers(memberDTOs);
            return dto;
        }
}
