package com.example.todolist.services;

import com.example.todolist.model.Person;
import com.example.todolist.model.dto.UserDTO;


public interface UserService {
    Person registerUser(UserDTO userDTO);

    String verify(UserDTO userDTO);
    Person getUserById(long userId);
}
