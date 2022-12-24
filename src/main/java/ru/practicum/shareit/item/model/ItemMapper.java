package ru.practicum.shareit.item.model;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoShort;
import ru.practicum.shareit.item.model.ItemDtoVeryShort;

@Component
public class ItemMapper {


    public static ItemDtoShort itemToItemShort(Item item){
        return ItemDtoShort.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public ItemDtoVeryShort itemToItemVeryShort(Item item){
        return ItemDtoVeryShort.builder()
                .id(item.getId())
                .name(item.getName())
                .build();
    }

//    public  ItemDtoForOwner  itemToItemDtoForOwner(Item item){
//        return ItemDtoForOwner.builder()
//        .id(item.getId())
//                .name(item.getName())
//                .description(item.getDescription())
//                .available(item.getAvailable())
//                .build();
//    }



    public static ItemDtoForOwner toItemDtoForOwner(Item item, Booking last, Booking next) {
        ItemDtoForOwner itemDtoForOwner = new ItemDtoForOwner();
        itemDtoForOwner.setId(item.getId());
        itemDtoForOwner.setName(item.getName());
        itemDtoForOwner.setDescription(item.getDescription());
        itemDtoForOwner.setAvailable(item.getAvailable());
        itemDtoForOwner.setOwnerId(item.getOwner().getId());
       // itemDtoForOwner.setRequestId(item.getRequestId());
     //   itemDtoForOwner.setComments(comments);
        if (last != null) {
            itemDtoForOwner.setLastBooking(
                    new ItemDtoForOwner.BookingInfo(
                            last.getId(),
                            last.getBooker().getId(),
                            last.getStart(),
                            last.getEnd()
                    )
            );
        }
        if (next != null) {
            itemDtoForOwner.setNextBooking(
                    new ItemDtoForOwner.BookingInfo(
                            next.getId(),
                            next.getBooker().getId(),
                            next.getStart(),
                            next.getEnd()
                    )
            );
        }
        return itemDtoForOwner;
    }


    public static ItemDtoForBooker toItemDtoForBooker(Item item) {
        ItemDtoForBooker itemDtoForBooker = new ItemDtoForBooker();
        itemDtoForBooker.setId(item.getId());
        itemDtoForBooker.setName(item.getName());
        itemDtoForBooker.setDescription(item.getDescription());
        itemDtoForBooker.setAvailable(item.getAvailable());
        itemDtoForBooker.setOwnerId(item.getOwner().getId());
      //  itemDtoForBooker.setComments(comments);
        return itemDtoForBooker;
    }





}
