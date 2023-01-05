package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ItemDtoShort extends ItemDtoAbstract {
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Long requestId;
}
