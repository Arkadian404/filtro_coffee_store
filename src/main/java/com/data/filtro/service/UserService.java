package com.data.filtro.service;

import com.data.filtro.model.User;
import com.data.filtro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }
}
