package com.vitaliyhtc.tasksboard.model.comparators;

import com.vitaliyhtc.tasksboard.model.User;

import java.util.Comparator;

public class UserIdComparator implements Comparator<User> {
    @Override
    public int compare(User user1, User user2) {
        return user1.getId().compareTo(user2.getId());
    }
}
