package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;


@Component
@RequiredArgsConstructor
public class Validator {

    private final UserRepository userRepository;


    public void validateDublicateEmail(List<User> users, String eMail) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(eMail))
                throw new EmailWrongException("адрес указанной электронной почты уже сущетсвует ");
        }
    }

    public void validateIncorrectEmail(User user) {
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(user.getEmail())) {
            throw new IncorrectEmailException("ошибка в написании электронной поты");
        }
    }

    public void validateNoEmail(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new NoEmailException("адресс электронной почты не может быть пустым");
        }
    }

    public void validateItemEmptyDescription(Item item) {
        if (item.getDescription() == null) {
            throw new NoItemNameException("нужно указать описание");
        }
    }

    public void validateItemEmptyName(Item item) {
        if (item.getName().isBlank()) {
            throw new NoItemNameException("нужно указать имя");
        }
    }


    public void validateItemWithOutEvailable(Item item) {
        if (item.getAvailable() == null) {
            throw new NoAvailableException("нужно указать состояние");
        }
    }


    public void validateUserNotFound(long userId) {
        boolean userExist = false;

        for (int i = 0; i < userRepository.getUsers().size(); i++) {
            if (userRepository.getUsers().get(i).getId() == userId)
                userExist = true;
        }
        if (userExist == false) throw new NotFoundException("юзера с таким номером нет");
    }



}
