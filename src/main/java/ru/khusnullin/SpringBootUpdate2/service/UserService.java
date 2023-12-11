package ru.khusnullin.SpringBootUpdate2.service;

import ru.khusnullin.SpringBootUpdate2.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    void addUserByAdmin(User user, String roleAdmin);
    void deleteUser(Long id);
    User getUserById(Long id);
    User getUserByEmail(String email);
    void updateUserByAdmin(User user, String roleAdmin);
    List<User> listUsers();
}
