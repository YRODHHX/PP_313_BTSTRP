package ru.uvuk.pp311.PP311.service;

import ru.uvuk.pp311.PP311.model.User;

import java.util.List;

public interface UserService {

    List<User> showAllUsers();

    User showUserById(Long id);

    User saveUser(User user);

    void deleteUserById(Long id);

    User findUserByUsername(String username);

}
