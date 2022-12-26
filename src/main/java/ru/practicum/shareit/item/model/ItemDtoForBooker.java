package ru.practicum.shareit.item.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDtoOut;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoForBooker extends ItemDtoAbstract {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private long ownerId;
    private long requestId;
    private BookingForBooker lastBooking;
    private BookingForBooker nextBooking;
    private Collection<CommentDtoOut> comments;

    public static class BookingForBooker {
    }
}
