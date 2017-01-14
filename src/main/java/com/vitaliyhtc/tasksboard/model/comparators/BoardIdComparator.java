package com.vitaliyhtc.tasksboard.model.comparators;

import com.vitaliyhtc.tasksboard.model.Board;

import java.util.Comparator;

public class BoardIdComparator implements Comparator<Board> {
    @Override
    public int compare(Board board1, Board board2) {
        return board1.getId().compareTo(board2.getId());
    }
}
