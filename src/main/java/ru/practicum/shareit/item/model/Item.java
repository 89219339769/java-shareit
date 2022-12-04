package ru.practicum.shareit.item.model;

import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class Item {
    private Long itemId;
    private Long userId;
    private String name;
    private String descroption;
    private boolean available;

}
