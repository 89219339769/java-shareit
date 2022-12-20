package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }


    @GetMapping("/{id}")
    public Optional<User> findUserById(@PathVariable Long id) {
        return userService.get(id);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }


}
