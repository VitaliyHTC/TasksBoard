package com.vitaliyhtc.tasksboard.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vitaliyhtc.tasksboard.model.comparators.BoardIdComparator;
import com.vitaliyhtc.tasksboard.model.comparators.GroupIdComparator;
import com.vitaliyhtc.tasksboard.model.comparators.TaskItemIdComparator;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;
import java.util.SortedSet;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "accountNonLocked")
    private boolean accountNonLocked;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    /* FetchType.EAGER - replaced by LAZY.
     * <prop key="hibernate.enable_lazy_load_no_trans">true</prop>
     * With this ^^^ property LAZY work fine after session close.
     */
    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles;

    @ManyToMany (mappedBy = "users")
    @SortComparator(BoardIdComparator.class)
    @JsonIgnore
    private SortedSet<Board> boards;

    @ManyToMany (mappedBy = "users")
    @SortComparator(TaskItemIdComparator.class)
    @JsonIgnore
    private SortedSet<TaskItem> taskItems;

    @ManyToMany (mappedBy = "users")
    @SortComparator(GroupIdComparator.class)
    @JsonIgnore
    private SortedSet<Group> groups;
    //http://www.concretepage.com/hibernate-4/hibernate-4-sortcomparator-and-sortnatural-example-for-sortedset-mapping

    public User() {
        this.enabled=false;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public SortedSet<Board> getBoards() {
        return boards;
    }
    public void setBoards(SortedSet<Board> boards) {
        this.boards = boards;
    }

    public SortedSet<TaskItem> getTaskItems() {
        return taskItems;
    }
    public void setTaskItems(SortedSet<TaskItem> taskItems) {
        this.taskItems = taskItems;
    }

    public SortedSet<Group> getGroups() {
        return groups;
    }
    public void setGroups(SortedSet<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int compareTo(User o) {
        return id.compareTo(o.getId());
    }

    @Override
    public String toString() {
        return "User@" + id;
    }
}
