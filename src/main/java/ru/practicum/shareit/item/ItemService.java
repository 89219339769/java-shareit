package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.validation.*;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.validation.Validation;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserNotFound userNotFound;
    private final List<ItemValidation> validations;

    public Item save(long userId, Item item) {
        item.setUserId(userId);
        userNotFound.validate(userId);
        validations.stream().
                forEach(validator -> validator.validate(item));
        return itemRepository.save(item);
    }

    public Item findItemById(Long id) {
        return itemRepository.findItemById(id);
    }
}
