package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.Board;
import com.vitaliyhtc.tasksboard.model.User;
import com.vitaliyhtc.tasksboard.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Board findById(Long id) {
        return boardRepository.findOne(id);
    }

    @Override
    public Board findByBoardName(String boardName) {
        return boardRepository.findByBoardName(boardName);
    }

    @Override
    public void addBoard(Board board) {
        boardRepository.saveAndFlush(board);
    }

    @Override
    public void updateBoard(Board board) {
        boardRepository.saveAndFlush(board);
    }

    @Override
    public void removeBoard(Board board) {
        boardRepository.delete(board);
    }

    @Override
    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    // return all boards that user is connected to them
    @Override
    public List<Board> findByUser(User user) {
        List<Board> allBoards = getAll();
        List<Board> boardsList = new ArrayList<>();
        for (Board board : allBoards) {
            if(board.getUsers().contains(user)){
                boardsList.add(board);
            }
        }
        return boardsList;
    }

    // return all boards that user can only view
    @Override
    public List<Board> findViewOnlyByUser(User user) {
        List<Board> allBoards = getAll();
        List<Board> viewOnlyBoards = new ArrayList<>();
        for (Board board : allBoards) {
            if(!board.getUsers().contains(user)){
                // Private, Group, Public
                if(board.getBoardVisibility().getVisName().equals("Public")){
                    viewOnlyBoards.add(board);
                }
                if(board.getBoardVisibility().getVisName().equals("Group")){
                    if(board.getGroup()!=null){
                        if(user.getGroups().contains(board.getGroup())){
                            viewOnlyBoards.add(board);
                        }
                    }
                }
            }
        }
        return viewOnlyBoards;
    }

    @Override
    public Boolean getUserCanOnlyView(User user, Board board) {
        if(board.getUsers().contains(user)){ return false; }
        return findViewOnlyByUser(user).contains(board);
    }

    @Override
    public Boolean getUserCanEdit(User user, Board board) {
        return board.getUsers().contains(user);
    }
}
