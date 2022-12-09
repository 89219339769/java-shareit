package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.EmailWrongException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;
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
        validations.stream()
                .forEach(validator -> validator.validate(user));
        return repository.create(user);
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        for (int j = 0; j < repository.getUsers().size(); j++) {
            if (repository.getUsers().get(j).getEmail().equals(user.getEmail()))
                throw new EmailWrongException("адрес указанной обновляемой электронной почты уже сущетсвует ");
        }
        for (int i = 0; i < repository.getUsers().size(); i++) {
            if (repository.getUsers().get(i).getId().equals(id)) {
                User updateUser = repository.getUsers().get(i);
                if (user.getEmail() != null && user.getEmail() != updateUser.getEmail()) {
                    validations.stream()
                                    .forEach(validator -> validator.validate(user));
                    updateUser.setEmail(user.getEmail());
                }
                if (user.getName() != null && user.getName() != updateUser.getName()) {
                    updateUser.setName(user.getName());
                }
                return updateUser;
            }
        }
        throw new NotFoundException("невозможно обновить, т.к. пользователя с этим номером не существует ");
    }

    public User get(Long id) {
        return repository.get(id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}