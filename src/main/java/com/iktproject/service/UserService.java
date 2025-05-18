package com.iktproject.service;

import com.iktproject.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    User save(User user);

    User update(Long id, User user);

    void delete(Long id);
}