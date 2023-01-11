package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.comment.CommentDtoOut;

import java.util.Collection;


@ToString
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
