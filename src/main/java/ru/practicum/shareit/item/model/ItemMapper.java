package ru.practicum.shareit.item.model;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoShort;
import ru.practicum.shareit.item.model.ItemDtoVeryShort;

@Component
public class ItemMapper {


    public static ItemDtoShort itemToItemShort(Item item){
        return ItemDtoShort.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public ItemDtoVeryShort itemToItemVeryShort(Item item){
        return ItemDtoVeryShort.builder()
                .id(item.getId())
                .name(item.getName())
                .build();
    }

    public  ItemDtoForOwner  itemToItemDtoForOwner(Item item){
        return ItemDtoForOwner.builder()
        .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }




}
