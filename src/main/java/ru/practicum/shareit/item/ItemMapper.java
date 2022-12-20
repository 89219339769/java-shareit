package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoShort;

@Component
public class ItemMapper {


    public Item ItemShortToItem(ItemDtoShort itemDtoShort){
        return Item.builder()
                .id(itemDtoShort.getId())
                .name(itemDtoShort.getName())
                .description(itemDtoShort.getDescription())
                .available(itemDtoShort.getAvailable())
                .build();
    }

}
