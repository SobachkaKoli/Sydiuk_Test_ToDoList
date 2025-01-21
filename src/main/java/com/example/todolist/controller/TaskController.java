package com.example.todolist.controller;

import com.example.todolist.model.Person;
import com.example.todolist.model.Task;
import com.example.todolist.model.UserPrincipal;
import com.example.todolist.model.dto.TaskDTO;
import com.example.todolist.services.TaskService;
import com.example.todolist.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody TaskDTO taskDTO, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Person person = userService.getUserById(userPrincipal.getId());
        Task createdTask = taskService.addTask(taskDTO, person);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    @PostMapping("/with-file")
    public ResponseEntity<Task> addTaskWithFile(@RequestPart("taskDTO") TaskDTO taskDTO,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @RequestPart("file") MultipartFile file) {
        Person person = userService.getUserById(userPrincipal.getId());
        Task createdTask = taskService.addTaskWithFile(taskDTO,person, file);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
