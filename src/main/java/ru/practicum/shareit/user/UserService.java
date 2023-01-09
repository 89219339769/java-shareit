package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    List<User> getAllUsers();

    User get(Long id);

    void delete(Long id);

    User updateUser(Long id, User user);
}
