package ru.practicum.shareit.item.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserNotFound {
    private final UserRepository repository;
    public void validate(long userId){
        boolean userExist = false;

        for (int i = 0; i < repository.getUsers().size(); i++) {
            if (repository.getUsers().get(i).getId() == userId)
            userExist = true;
        }
        if( userExist==false)throw  new NotFoundException("юзера с таким номером нет");
    }
}
