package com.example.todolist.services.impl;

import com.example.todolist.model.Person;
import com.example.todolist.model.Role;
import com.example.todolist.model.dto.UserDTO;
import com.example.todolist.repo.UserRepository;
import com.example.todolist.services.JWTService;
import com.example.todolist.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public UserServiceImpl(UserRepository userRepository, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Person registerUser(UserDTO userDTO) {
        Person user = new Person();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setRoles(List.of(Role.USER));
        return userRepository.save(user);
    }

    @Override
    public String verify(UserDTO userDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        if (authentication.isAuthenticated())
            return jwtService.generateToken(userDTO.getUsername());

        return "fail";

    }

    @Override
    public Person getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}