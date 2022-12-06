package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.validation.EmptyDescription;
import ru.practicum.shareit.item.validation.EmptyName;
import ru.practicum.shareit.item.validation.ItemWithoutAvailable;
import ru.practicum.shareit.item.validation.UserNotFound;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemWithoutAvailable itemWithoutAvailable;
    private final ItemRepository itemRepository;
    private final UserRepository repository;
    private final UserNotFound userNotFound;

    private final EmptyName emptyName;
    private final EmptyDescription emptyDescription;


    public Item save(long userId, Item item) {
        item.setUserId(userId);
        userNotFound.validate(userId);
        itemWithoutAvailable.validate(item);
        emptyName.validate(item);
        emptyDescription.validate(item);
        return itemRepository.save(item);
    }


    public Item findItemById(Long id) {
        return itemRepository.findItemById(id);
    }

}
