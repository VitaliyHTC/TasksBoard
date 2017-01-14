package com.vitaliyhtc.tasksboard.model.comparators;

import com.vitaliyhtc.tasksboard.model.Group;

import java.util.Comparator;

public class GroupIdComparator implements Comparator<Group> {
    @Override
    public int compare(Group group1, Group group2) {
        Long id1 = group1.getId();
        Long id2 = group2.getId();
        return id1.compareTo(id2);
    }
}
