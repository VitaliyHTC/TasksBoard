package com.vitaliyhtc.tasksboard.repositories;

import com.vitaliyhtc.tasksboard.model.TaskItem;
import com.vitaliyhtc.tasksboard.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskItemRepository extends JpaRepository<TaskItem, Long> {

    List<TaskItem> findByTaskList(TaskList taskList);
}
