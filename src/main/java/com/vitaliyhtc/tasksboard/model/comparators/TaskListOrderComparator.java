package com.vitaliyhtc.tasksboard.model.comparators;

import com.vitaliyhtc.tasksboard.model.TaskList;

import java.util.Comparator;

public class TaskListOrderComparator implements Comparator<TaskList> {
    @Override
    public int compare(TaskList taskItem1, TaskList taskItem2){
        Long id1 = taskItem1.getId();
        Long id2 = taskItem2.getId();
        return id1.compareTo(id2);
    }
}
