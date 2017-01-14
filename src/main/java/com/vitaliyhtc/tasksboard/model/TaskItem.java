package com.vitaliyhtc.tasksboard.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vitaliyhtc.tasksboard.model.comparators.UserIdComparator;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.util.Date;
import java.util.SortedSet;

@Entity
@Table(name = "task_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TaskItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name ="due_date")
    private Date dueDate;

    @Column(name = "task_item_order")
    private int taskItemOrder;

    @ManyToOne
    @JoinColumn(name = "list_id", foreignKey = @ForeignKey(name = "LIST_ID_FK"))
    private TaskList taskList;

    @ManyToMany
    @JoinTable(name = "user_task_item", joinColumns = @JoinColumn(name = "task_item_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @SortComparator(UserIdComparator.class)
    private SortedSet<User> users;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getTaskItemOrder() {
        return taskItemOrder;
    }
    public void setTaskItemOrder(int taskItemOrder) {
        this.taskItemOrder = taskItemOrder;
    }

    public TaskList getTaskList() {
        return taskList;
    }
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public SortedSet<User> getUsers() {
        return users;
    }
    public void setUsers(SortedSet<User> users) {
        this.users = users;
    }
}
