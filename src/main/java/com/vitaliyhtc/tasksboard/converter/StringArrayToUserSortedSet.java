package com.vitaliyhtc.tasksboard.converter;

import com.vitaliyhtc.tasksboard.model.User;
import com.vitaliyhtc.tasksboard.model.comparators.UserIdComparator;
import com.vitaliyhtc.tasksboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by VitaliyHTC on 21.11.2016.
 */
@Component
public final class StringArrayToUserSortedSet implements Converter<String[], SortedSet<User>> {

    @Autowired
    private UserService userService;

    @Override
    public synchronized SortedSet<User> convert(String[] stringArray){
        SortedSet<User> usersSortedSet = new TreeSet<>(new UserIdComparator());
        for (String stringItem : stringArray) {
            Long id = Long.parseLong(stringItem.substring(stringItem.lastIndexOf("@")+1));
            usersSortedSet.add(userService.findById(id));
        }
        return usersSortedSet;
    }
}
