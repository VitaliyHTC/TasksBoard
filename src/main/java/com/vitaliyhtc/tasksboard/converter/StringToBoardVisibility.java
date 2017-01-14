package com.vitaliyhtc.tasksboard.converter;

import com.vitaliyhtc.tasksboard.model.BoardVisibility;
import com.vitaliyhtc.tasksboard.service.BoardVisibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public final class StringToBoardVisibility implements Converter<String, BoardVisibility> {

    @Autowired
    private BoardVisibilityService boardVisibilityService;

    @Override
    public synchronized BoardVisibility convert(String s){
        return boardVisibilityService.findById(Long.parseLong(s));
    }

}
