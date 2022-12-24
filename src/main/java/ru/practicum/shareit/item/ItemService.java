package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.ItemDtoForOwner;
import ru.practicum.shareit.item.model.ItemDtoShort;

public interface ItemService {


    ItemDtoForOwner findItemById(Long id, Long ownerId);
}
