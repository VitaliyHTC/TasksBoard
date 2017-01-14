package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.BoardVisibility;

import java.util.List;

public interface BoardVisibilityService {
    BoardVisibility findById (Long id);
    List<BoardVisibility> getAll();
}
