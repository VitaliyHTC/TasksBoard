package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.BoardVisibility;
import com.vitaliyhtc.tasksboard.repositories.BoardVisibilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardVisibilityServiceImpl implements BoardVisibilityService  {

    @Autowired
    private BoardVisibilityRepository boardVisibilityRepository;

    @Override
    public BoardVisibility findById(Long id) {
        return boardVisibilityRepository.findOne(id);
    }

    @Override
    public List<BoardVisibility> getAll() {
        return boardVisibilityRepository.findAll();
    }
}
