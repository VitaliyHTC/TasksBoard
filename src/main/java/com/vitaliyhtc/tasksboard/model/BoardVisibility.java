package com.vitaliyhtc.tasksboard.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "board_visibility")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BoardVisibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vis_name")
    private String visName;
    // Private, Group, Public

    @Column(name = "vis_description")
    private String visDescription;

    @OneToMany(mappedBy = "boardVisibility")
    @JsonIgnore
    private Set<Board> boards;

    public Long getId() {
        return id;
    }
    public String getVisName() {
        return visName;
    }
    public String getVisDescription() {
        return visDescription;
    }
    public Set<Board> getBoards() {
        return boards;
    }

}
