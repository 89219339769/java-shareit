package ru.practicum.shareit.item.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;

import java.util.ArrayList;
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    public Item save(long userId,Item item) {
        item.setUserId(userId);
       return itemRepository.save(item);
    }

}
