package com.vitaliyhtc.tasksboard.validator;

import com.vitaliyhtc.tasksboard.model.Board;
import com.vitaliyhtc.tasksboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by VitaliyHTC on 18.11.2016.
 */

@Controller
public class BoardValidator implements Validator {

    @Autowired
    private BoardService boardService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Board.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors){
        Board board = (Board) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "boardName", "Required");
        if(board.getBoardName().length() > 255){
            errors.rejectValue("boardName", "Size.boardForm.boardName");
        }
        if(boardService.findByBoardName(board.getBoardName())!=null){
            errors.rejectValue("boardName", "Duplicate.boardForm.boardName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "boardDescription", "Required");
        if(board.getBoardDescription().length()>1024){
            errors.rejectValue("boardDescription", "Size.boardForm.boardDescription");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "boardVisibility", "Required");

    }
}
