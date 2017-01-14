package com.vitaliyhtc.tasksboard.validator;

import com.vitaliyhtc.tasksboard.model.Group;
import com.vitaliyhtc.tasksboard.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by VitaliyHTC on 17.11.2016.
 */

@Controller
public class GroupUpdateValidator implements Validator {

    @Autowired
    private GroupService groupService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Group.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors){
        Group group = (Group) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupName", "Required");
        if(group.getGroupName().length() > 255){
            errors.rejectValue("groupName", "Size.groupForm.groupName");
        }
        Group duplicateGroup = groupService.findByGroupName(group.getGroupName());
        if(duplicateGroup!=null){
            if(duplicateGroup.getId()!=group.getId()){
                errors.rejectValue("groupName", "Duplicate.groupForm.groupName");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupDescription", "Required");
        if(group.getGroupDescription().length()>1024){
            errors.rejectValue("groupDescription", "Size.groupForm.groupDescription");
        }
    }
}
