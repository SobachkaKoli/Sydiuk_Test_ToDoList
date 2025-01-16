package com.example.todolist.services;

import com.example.todolist.model.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User registerUser(String username, String password);
}
