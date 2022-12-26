package ru.practicum.shareit.item.model;


import lombok.*;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDtoOut;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ItemDtoForOwner extends ItemDtoAbstract {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private long ownerId;
    private long requestId;
    private BookingInfo lastBooking;
    private BookingInfo nextBooking;
    private Collection<CommentDtoOut> comments;

    @ToString
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookingInfo {
        private long id;
        private long bookerId;
        private LocalDateTime start;
        private LocalDateTime end;
    }

}