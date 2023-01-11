package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NoEmailException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.exceptions.IncorrectEmailException;
import ru.practicum.shareit.exceptions.NoAvailableException;
import ru.practicum.shareit.exceptions.NoItemNameException;
import ru.practicum.shareit.user.model.User;


@Component
@RequiredArgsConstructor
public class Validator {

    public void validateIncorrectEmail(User user) {
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(user.getEmail())) {
            throw new IncorrectEmailException("ошибка в написании электронной почты");
        }
    }

    public void validateNoEmail(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new NoEmailException("адресс электронной почты не может быть пустым");
        }
    }

    public void validateItemEmptyDescription(Item item) {
        if (item.getDescription() == null) {
            throw new NoItemNameException("нужно указать необходимой желаемой вещи");
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
}
