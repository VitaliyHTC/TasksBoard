package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.TaskItem;
import com.vitaliyhtc.tasksboard.model.TaskList;

import java.util.List;

public interface TaskItemService {
    TaskItem findById(Long id);
    void addTaskItem(TaskItem taskItem);
    void updateTaskItem(TaskItem taskItem);
    void removeTaskItem(TaskItem taskItem);
    List<TaskItem> findByTaskList(TaskList taskList);
}
