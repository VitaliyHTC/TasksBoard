package com.vitaliyhtc.tasksboard.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vitaliyhtc.tasksboard.model.comparators.TaskItemIdComparator;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.util.SortedSet;

@Entity
@Table(name = "lists")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "list_name")
    private String listName;

    @Column(name = "list_description")
    private String listDescription;

    @Column(name = "items_limit")
    private int itemsLimit;

    @Column(name = "list_order")
    private int listOrder;

    @ManyToOne
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "BOARD_ID_FK") )
    @JsonIgnore
    private Board board;

    @OneToMany(mappedBy = "taskList")
    @SortComparator(TaskItemIdComparator.class)
    private SortedSet<TaskItem> taskItemSet;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }
    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListDescription() {
        return listDescription;
    }
    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public int getItemsLimit() {
        return itemsLimit;
    }
    public void setItemsLimit(int itemsLimit) {
        this.itemsLimit = itemsLimit;
    }

    public int getListOrder() {
        return listOrder;
    }
    public void setListOrder(int listOrder) {
        this.listOrder = listOrder;
    }

    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public SortedSet<TaskItem> getTaskItemSet() {
        return taskItemSet;
    }
    public void setTaskItemSet(SortedSet<TaskItem> taskItemSet) {
        this.taskItemSet = taskItemSet;
    }
}
