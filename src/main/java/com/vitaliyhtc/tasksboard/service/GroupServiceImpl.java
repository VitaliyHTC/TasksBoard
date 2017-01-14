package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.Group;
import com.vitaliyhtc.tasksboard.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group findById(Long id) {
        return groupRepository.findOne(id);
    }

    @Override
    public Group findByGroupName(String groupName) {
        return groupRepository.findByGroupName(groupName);
    }

    @Override
    public void addGroup(Group group) {
        groupRepository.saveAndFlush(group);
    }

    @Override
    public void updateGroup(Group group) {
        groupRepository.saveAndFlush(group);
    }

    @Override
    public void removeGroup(Group group) {
        groupRepository.delete(group);
    }

    @Override
    public List<Group> getAll() {
        return groupRepository.findAll();
    }
}
