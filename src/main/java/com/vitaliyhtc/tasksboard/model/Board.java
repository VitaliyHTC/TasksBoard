package com.vitaliyhtc.tasksboard.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vitaliyhtc.tasksboard.model.comparators.TaskListOrderComparator;
import com.vitaliyhtc.tasksboard.model.comparators.UserIdComparator;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import java.util.SortedSet;

@Entity
@Table(name = "boards")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_name")
    private String boardName;

    @Column(name = "board_description")
    private String boardDescription;

    @ManyToOne
    @JoinColumn(name = "board_visibility", foreignKey = @ForeignKey(name = "VISIBILITY_ID_FK") )
    private BoardVisibility boardVisibility;

    @ManyToMany
    @JoinTable (name = "user_boards", joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @SortComparator(UserIdComparator.class)
    @JsonIgnore
    private SortedSet<User> users;

    @OneToMany(mappedBy = "board")
    @SortComparator(TaskListOrderComparator.class)
    private SortedSet<TaskList> taskListSet;

    @ManyToOne
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "GROUP_ID_FK") )
    @JsonIgnore
    private Group group;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getBoardName() {
        return boardName;
    }
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardDescription() {
        return boardDescription;
    }
    public void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }

    public BoardVisibility getBoardVisibility() {
        return boardVisibility;
    }
    public void setBoardVisibility(BoardVisibility boardVisibility) {
        this.boardVisibility = boardVisibility;
    }

    public SortedSet<User> getUsers() {
        return users;
    }
    public void setUsers(SortedSet<User> users) {
        this.users = users;
    }

    public SortedSet<TaskList> getTaskListSet() {
        return taskListSet;
    }
    public void setTaskListSet(SortedSet<TaskList> taskListSet) {
        this.taskListSet = taskListSet;
    }

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return id != null ? id.equals(board.id) : board.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Board@" + id;
    }
}
