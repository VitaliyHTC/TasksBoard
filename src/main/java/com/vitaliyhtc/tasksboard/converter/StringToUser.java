package com.vitaliyhtc.tasksboard.converter;

import com.vitaliyhtc.tasksboard.model.User;
import com.vitaliyhtc.tasksboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by VitaliyHTC on 22.11.2016.
 */
@Component
public final class StringToUser implements Converter<String, User> {

    @Autowired
    private UserService userService;

    @Override
    public synchronized User convert(String s) {
        return userService.findById(Long.parseLong(s.substring(s.lastIndexOf("@")+1)));
    }
}
