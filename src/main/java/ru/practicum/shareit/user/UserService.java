package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.validation.DublicateEmail;
import ru.practicum.shareit.user.validation.Validation;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final List<Validation> validations;

    public List<User> getAllUsers() {
        return repository.getAll();
    }

    public User saveUser(User user) {

        validations.stream().
                forEach(validator -> validator.validate(user));
        return repository.create(user);
    }
}