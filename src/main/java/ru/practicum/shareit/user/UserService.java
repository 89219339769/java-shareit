package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.Validator;
import ru.practicum.shareit.exceptions.EmailWrongException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final
    Validator validator;
    private final UserRepository repository;

    public List<User> getAllUsers() {
        return repository.getAll();
    }

    public User saveUser(User user) {
        List<User> users = repository.getUsers();
        String userEmail = user.getEmail();
        validator.validateDublicateEmail(users, userEmail);
       //по аналогии остальные методы сделать (обращение к базе тут обработка там)
       //Сделать 2 класса validator (один для user один для item)

        validator.validateNoEmail(user);
        validator.validateIncorrectEmail(user);

        return repository.create(user);
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        for (int j = 0; j < repository.getUsers().size(); j++) {
            if (repository.getUsers().get(j).getEmail().equals(user.getEmail()))
                throw new EmailWrongException("адрес указанной обновляемой электронной почты уже сущетсвует ");
        }

        if (repository.getUsers().get((int) (id-1)) == null) {
            throw new NotFoundException("невозможно обновить, т.к. пользователя с этим номером не существует ");}

        User updateUser = repository.getUsers().get(Math.toIntExact(id) - 1);
        if (user.getEmail() != null && user.getEmail() != updateUser.getEmail()) {
            validator.validateNoEmail(user);
            validator.validateDublicateEmail( repository.getUsers(),user.getEmail());
            validator.validateIncorrectEmail(user);

            updateUser.setEmail(user.getEmail());
        }
        if (user.getName() != null && user.getName() != updateUser.getName()) {
            updateUser.setName(user.getName());
        }
        return updateUser;
    }

    public User get(Long id) {
        return repository.get(id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}