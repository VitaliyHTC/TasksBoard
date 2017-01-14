package com.vitaliyhtc.tasksboard.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
