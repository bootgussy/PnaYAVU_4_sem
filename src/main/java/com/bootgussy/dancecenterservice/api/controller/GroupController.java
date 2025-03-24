package com.bootgussy.dancecenterservice.api.controller;

import com.bootgussy.dancecenterservice.api.dto.create.GroupCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.GroupResponseDto;
import com.bootgussy.dancecenterservice.core.mapper.GroupMapper;
import com.bootgussy.dancecenterservice.core.model.Group;
import com.bootgussy.dancecenterservice.core.service.GroupService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @GetMapping("/{id}")
    public GroupResponseDto findGroupById(@PathVariable Long id) {
        Group group = groupService.findGroupById(id);
        return groupMapper.toResponseDto(group);
    }

    @GetMapping
    public List<GroupResponseDto> findAllGroups() {
        List<Group> groups = groupService.findAllGroups();
        return groupMapper.toResponseDtoList(groups);
    }

    @PostMapping
    public GroupResponseDto createGroup(@RequestBody GroupCreateDto createDto) {
        Group group = groupMapper.toEntity(createDto);
        Group createdGroup = groupService.createGroup(group);
        return groupMapper.toResponseDto(createdGroup);
    }

    @PutMapping("/{id}")
    public GroupResponseDto updateGroup(@PathVariable Long id, @RequestBody GroupCreateDto createDto) {
        Group group = groupMapper.toEntity(createDto);
        group.setId(id);
        Group updatedGroup = groupService.updateGroup(group);
        return groupMapper.toResponseDto(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }
}