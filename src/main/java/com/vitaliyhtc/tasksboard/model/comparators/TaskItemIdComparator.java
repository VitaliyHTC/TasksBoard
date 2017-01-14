package com.vitaliyhtc.tasksboard.model.comparators;

import com.vitaliyhtc.tasksboard.model.TaskItem;

import java.util.Comparator;

public class TaskItemIdComparator implements Comparator<TaskItem> {
    @Override
    public int compare(TaskItem taskItem1, TaskItem taskItem2){
        Long id1 = taskItem1.getId();
        Long id2 = taskItem2.getId();
        return id1.compareTo(id2);
    }
}
