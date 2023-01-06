package ru.practicum.shareit.item.model;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingForItem;
import ru.practicum.shareit.comment.CommentDtoOut;

import java.util.Collection;

@Component
public class ItemMapper {
    public static ItemDtoShort itemToItemShort(Item item) {
        return ItemDtoShort.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequestId())
                .build();
    }

    public static ItemDtoForOwner toItemDtoForOwner(Item item, Booking last, Booking next, Collection<CommentDtoOut> comments) {
        ItemDtoForOwner itemDtoForOwner = new ItemDtoForOwner();
        itemDtoForOwner.setId(item.getId());
        itemDtoForOwner.setName(item.getName());
        itemDtoForOwner.setDescription(item.getDescription());
        itemDtoForOwner.setAvailable(item.getAvailable());
        itemDtoForOwner.setOwnerId(item.getOwner().getId());
        itemDtoForOwner.setComments(comments);
        if (last != null) {
            itemDtoForOwner.setLastBooking(
                    new BookingForItem(
                            last.getId(),
                            last.getBooker().getId(),
                            last.getStart(),
                            last.getEnd()
                    )
            );
        }
        if (next != null) {
            itemDtoForOwner.setNextBooking(
                    new BookingForItem(
                            next.getId(),
                            next.getBooker().getId(),
                            next.getStart(),
                            next.getEnd()
                    )
            );
        }
        return itemDtoForOwner;
    }

    public static ItemDtoForBooker toItemDtoForBooker(Item item, Collection<CommentDtoOut> comments) {
        ItemDtoForBooker itemDtoForBooker = new ItemDtoForBooker();
        itemDtoForBooker.setId(item.getId());
        itemDtoForBooker.setName(item.getName());
        itemDtoForBooker.setDescription(item.getDescription());
        itemDtoForBooker.setAvailable(item.getAvailable());
        itemDtoForBooker.setOwnerId(item.getOwner().getId());
        itemDtoForBooker.setComments(comments);
        return itemDtoForBooker;
    }

    public static ItemDtoForRequest itemToItemForRequest(Item item) {
        if (item.getRequestId() != null) {
            return ItemDtoForRequest.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .description(item.getDescription())
                    .available(item.getAvailable())
                    .requestId(item.getRequestId())
                    .build();
        }

        return ItemDtoForRequest.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }


}
