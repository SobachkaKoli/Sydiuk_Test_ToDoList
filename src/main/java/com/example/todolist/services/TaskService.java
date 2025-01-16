package com.example.todolist.services;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;

import java.util.List;

public interface TaskService {

    Task createTask(Task task, User user);
    List<Task> getTaskByUserId(Long userId);
    void deleteTask(Long taskId);
}
