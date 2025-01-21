package com.example.todolist.services.impl;

import com.example.todolist.model.Person;
import com.example.todolist.model.Task;
import com.example.todolist.repo.TaskRepository;
import com.example.todolist.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task, Person user) {
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTaskByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
