package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.EmailWrongException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.Validator;

import java.util.List;


@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {
    private Validator validator;
    private final UserRepository repository;

    @Override
    public User saveUser(User user) {
        List<User> users = repository.findAll();
        String userEmail = user.getEmail();
        validator.validateNoEmail(user);
        validator.validateIncorrectEmail(user);
        repository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User get(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id: " + id));
        return user;
    }

    public void delete(Long id) {
        repository.deleteById(id);

    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        for (int j = 0; j < repository.findAll().size(); j++) {
            if (repository.findAll().get(j).getEmail().equals(user.getEmail()))
                throw new EmailWrongException("адрес указанной обновляемой электронной почты уже сущетсвует ");
        }

        List<User> users = repository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                User updateUser = users.get(i);
                if (user.getEmail() != null && user.getEmail() != updateUser.getEmail()) {
                    validator.validateNoEmail(user);
                    validator.validateIncorrectEmail(user);
                    updateUser.setEmail(user.getEmail());
                }
                if (user.getName() != null && user.getName() != updateUser.getName()) {
                    updateUser.setName(user.getName());
                }


                repository.save(updateUser);
                return updateUser;
            }
        }
        throw new NotFoundException("невозможно обновить, т.к. пользователя с этим номером не существует ");
    }
}
