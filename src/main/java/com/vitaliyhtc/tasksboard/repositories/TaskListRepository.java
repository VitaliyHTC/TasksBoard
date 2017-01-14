package com.vitaliyhtc.tasksboard.repositories;

import com.vitaliyhtc.tasksboard.model.Board;
import com.vitaliyhtc.tasksboard.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {

    List<TaskList> findByBoard(Board board);
}
