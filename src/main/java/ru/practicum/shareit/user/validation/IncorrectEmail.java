package ru.practicum.shareit.user.validation;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.IncorrectEmailException;
import ru.practicum.shareit.user.model.User;
@Component
public class IncorrectEmail implements Validation{


    @Override
    public void validate(User user) {
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(user.getEmail())) {
           throw new IncorrectEmailException("ошибка в написании электронной поты") ;
        }
    }
}
