package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.ItemDtoAbstract;


public interface ItemService {
    ItemDtoAbstract findItemById(Long id, Long ownerId);

}
