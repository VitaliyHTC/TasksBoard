package com.vitaliyhtc.tasksboard.converter;

import com.vitaliyhtc.tasksboard.model.Group;
import com.vitaliyhtc.tasksboard.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by VitaliyHTC on 19.11.2016.
 */
public final class StringToGroup implements Converter<String, Group> {

    @Autowired
    private GroupService groupService;

    @Override
    public synchronized Group convert(String s) {
        Long groupID = Long.parseLong(s);
        if(groupID==0){
            return null;
        }else{
            return groupService.findById(groupID);
        }
    }
}
