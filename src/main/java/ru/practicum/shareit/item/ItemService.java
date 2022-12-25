package ru.practicum.shareit.item;

import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.item.model.ItemDtoAbstract;
import ru.practicum.shareit.item.model.ItemDtoForOwner;
import ru.practicum.shareit.item.model.ItemDtoShort;

public interface ItemService {


    ItemDtoAbstract findItemById(Long id, Long ownerId);




}
