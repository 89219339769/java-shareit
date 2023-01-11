package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoAbstract;
import ru.practicum.shareit.item.model.ItemDtoForOwner;
import ru.practicum.shareit.item.model.ItemDtoShort;

import java.util.Collection;
import java.util.List;


public interface ItemService {
    ItemDtoShort save(long userId, Item item);

    Item updateItem(Long itemId, Long userId, Item item);

    ItemDtoAbstract findItemById(Long id, Long ownerId);

    Collection<ItemDtoForOwner> getAllItems(long ownerId);

    List<ItemDtoShort> findItemByNameOrDescription(String query);
}
