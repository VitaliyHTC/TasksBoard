package com.vitaliyhtc.tasksboard.validator;

import com.vitaliyhtc.tasksboard.model.TaskList;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Controller
public class TaskListValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return TaskList.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TaskList taskList = (TaskList) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "listName", "Required");
        if(taskList.getListName().length() > 255){
            errors.rejectValue("listName", "Size.taskListForm.listName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "listDescription", "Required");
        if(taskList.getListDescription().length() > 1024){
            errors.rejectValue("listDescription", "Size.taskListForm.listDescription");
        }
    }
}
