package com.vitaliyhtc.tasksboard.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vitaliyhtc.tasksboard.model.comparators.BoardIdComparator;
import com.vitaliyhtc.tasksboard.model.comparators.UserIdComparator;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.SortedSet;

@Entity
@Table(name = "groups")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_description")
    private String groupDescription;

    @ManyToMany
    @JoinTable (name = "user_groups", joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @SortComparator(UserIdComparator.class)
    @JsonIgnore
    private SortedSet<User> users;

    @OneToMany(mappedBy = "group")
    @SortComparator(BoardIdComparator.class)
    @JsonIgnore
    private SortedSet<Board> boards;

    public Group() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }
    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public SortedSet<User> getUsers() {
        return users;
    }
    public void setUsers(SortedSet<User> users) {
        this.users = users;
    }

    public SortedSet<Board> getBoards() {
        return boards;
    }
    public void setBoards(SortedSet<Board> boards) {
        this.boards = boards;
    }

    @Override
    public String toString() {
        return "Group@" + id;
    }
}
