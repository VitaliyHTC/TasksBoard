package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.TaskItem;
import com.vitaliyhtc.tasksboard.model.TaskList;
import com.vitaliyhtc.tasksboard.repositories.TaskItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskItemServiceImpl implements TaskItemService {

    @Autowired
    private TaskItemRepository taskItemRepository;


    @Override
    public TaskItem findById(Long id) {
        return taskItemRepository.findOne(id);
    }

    @Override
    public void addTaskItem(TaskItem taskItem) {
        taskItemRepository.saveAndFlush(taskItem);
    }

    @Override
    public void updateTaskItem(TaskItem taskItem) {
        taskItemRepository.saveAndFlush(taskItem);
    }

    @Override
    public void removeTaskItem(TaskItem taskItem) {
        taskItemRepository.delete(taskItem);
    }

    @Override
    public List<TaskItem> findByTaskList(TaskList taskList) {
        return taskItemRepository.findByTaskList(taskList);
    }
}
