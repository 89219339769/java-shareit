package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.comment.Comment;

import java.util.List;

@Getter
@Setter
@Builder
public class ItemDtoShort extends ItemDtoAbstract{
    private Long id;

    private String name;

    private String description;

    private Boolean available;



}
