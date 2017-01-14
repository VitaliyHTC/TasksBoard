package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.Board;
import com.vitaliyhtc.tasksboard.model.TaskList;

import java.util.List;

public interface TaskListService {
    TaskList findById(Long id);
    void addTaskList(TaskList taskList);
    void updateTaskList(TaskList taskList);
    void removeTaskList(TaskList taskList);
    List<TaskList> findByBoard(Board board);

}
