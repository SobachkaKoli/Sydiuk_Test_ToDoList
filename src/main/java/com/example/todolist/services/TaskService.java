package com.example.todolist.services;

import com.example.todolist.model.Person;
import com.example.todolist.model.Task;

import java.util.List;

public interface TaskService {

    Task createTask(Task task, Person user);

    List<Task> getTaskByUserId(Long userId);

    void deleteTask(Long taskId);
}
