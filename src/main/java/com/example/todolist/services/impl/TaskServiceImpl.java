package com.example.todolist.services.impl;

import com.example.todolist.model.Person;
import com.example.todolist.model.Task;
import com.example.todolist.model.dto.TaskDTO;
import com.example.todolist.repo.TaskRepository;
import com.example.todolist.services.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final String uploadDir = "D:/uploads";

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task addTask(TaskDTO taskDTO, Person person) {
        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .user(person)
                .build();
        return taskRepository.save(task);
    }

    @Override
    public Task addTaskWithFile(TaskDTO taskDTO, Person person, MultipartFile file) {
        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .user(person)
                .build();

        try {

            File uploadDirectory = new File(uploadDir);

            if (!uploadDirectory.exists()) {
                boolean dirsCreated = uploadDirectory.mkdirs();
                if (!dirsCreated) {
                    throw new IOException("Failed to create directory: " + uploadDir);
                }
            }

            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // Унікальне ім'я
            String filePath = uploadDir + File.separator + uniqueFileName;

            File dest = new File(filePath);
            file.transferTo(dest);

            task.setFilePath(filePath);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        return taskRepository.save(task);
    }


    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
    }

    @Override
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

}
