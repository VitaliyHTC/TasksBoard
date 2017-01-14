package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.Board;
import com.vitaliyhtc.tasksboard.model.TaskList;
import com.vitaliyhtc.tasksboard.repositories.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;


    @Override
    public TaskList findById(Long id) {
        return taskListRepository.findOne(id);
    }

    @Override
    public void addTaskList(TaskList taskList) {
        taskListRepository.saveAndFlush(taskList);
    }

    @Override
    public void updateTaskList(TaskList taskList) {
        taskListRepository.saveAndFlush(taskList);
    }

    @Override
    public void removeTaskList(TaskList taskList) {
        taskListRepository.delete(taskList);
    }

    @Override
    public List<TaskList> findByBoard(Board board) {
        return taskListRepository.findByBoard(board);
    }
}
