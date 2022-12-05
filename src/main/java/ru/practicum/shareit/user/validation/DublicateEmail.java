package ru.practicum.shareit.user.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.EmailWrongException;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;


@Component
@RequiredArgsConstructor
public class DublicateEmail implements Validation {
    private final UserRepository userRepository;


    public void validate(User user) {
        String temp = user.getEmail();
        for (int i = 0; i < userRepository.getUsers().size(); i++) {
            if (userRepository.getUsers().get(i).getEmail().equals(temp))
                throw new EmailWrongException("адрес указанной электронной почты уже сущетсвует ");
        }
    }
}





