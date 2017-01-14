package com.vitaliyhtc.tasksboard.validator;

import com.vitaliyhtc.tasksboard.model.TaskItem;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Controller
public class TaskItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return TaskItem.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TaskItem taskItem = (TaskItem) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taskName", "Required");
        if(taskItem.getTaskName().length() > 255){
            errors.rejectValue("taskName", "Size.taskItemForm.taskName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taskDescription", "Required");
        if(taskItem.getTaskDescription().length() > 1024){
            errors.rejectValue("taskDescription", "Size.taskItemForm.taskDescription");
        }
    }
}
