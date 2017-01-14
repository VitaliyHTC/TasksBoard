package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.Board;
import com.vitaliyhtc.tasksboard.model.User;

import java.util.List;

public interface BoardService {
    Board findById(Long id);
    Board findByBoardName(String boardName);
    void addBoard(Board board);
    void updateBoard(Board board);
    void removeBoard(Board board);
    List<Board> getAll();
    List<Board> findByUser(User user);
    List<Board> findViewOnlyByUser(User user);
    Boolean getUserCanOnlyView(User user, Board board);
    Boolean getUserCanEdit(User user, Board board);

}
