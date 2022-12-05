package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
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

    public User updateUser(Long id, User user) {
       user.setId(id);
        for (int i = 0; i < repository.getUsers().size(); i++) {
            if(repository.getUsers().get(i).getId()==id){
       validations.stream().
               forEach(validator -> validator.validate(user));
                 return repository.update(user);
            }
        }
            throw new NotFoundException("пользователя с этим номером не существует");
    }
}