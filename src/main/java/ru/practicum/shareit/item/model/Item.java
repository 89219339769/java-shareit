package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.Getter;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class Item {
    private Long Id;
   private Long userId;
    private String name;
    private String description;
    private Boolean available;

}
