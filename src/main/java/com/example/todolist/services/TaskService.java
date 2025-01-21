package com.example.todolist.services;

import com.example.todolist.model.Person;
import com.example.todolist.model.Task;
import com.example.todolist.model.dto.TaskDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {

    Task addTask(TaskDTO taskDTO, Person person);

    Task addTaskWithFile(TaskDTO taskDTO,Person person, MultipartFile file);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task updateTask(Long id, Task taskDetails);

    void deleteTask(Long id);
}
