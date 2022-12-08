package ru.practicum.shareit.item.model;

import lombok.Data;

@Data
public class Item {
    private Long Id;
   private Long userId;
    private String name;
    private String description;
    private Boolean available;
}
