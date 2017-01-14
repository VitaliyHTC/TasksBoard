package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.Group;

import java.util.List;

public interface GroupService {
    Group findById(Long id);
    Group findByGroupName(String groupName);
    void addGroup(Group group);
    void updateGroup(Group group);
    void removeGroup(Group group);
    List<Group> getAll();
}
