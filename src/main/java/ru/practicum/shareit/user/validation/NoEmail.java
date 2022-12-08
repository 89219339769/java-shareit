package ru.practicum.shareit.user.validation;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NoEmailException;
import ru.practicum.shareit.user.model.User;



@Component
public class NoEmail implements Validation {

    @Override
    public void validate(User user) {
        if(user.getEmail()==null||user.getEmail().isBlank()){
            throw new NoEmailException("адресс электронной почты не может быть пустым");
        }
    }
}
