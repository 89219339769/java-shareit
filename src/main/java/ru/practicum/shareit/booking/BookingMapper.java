package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoShort;

@AllArgsConstructor
@Component
public class BookingMapper {

    private final ItemMapper itemMapper;


    public BookingDtoShort bookingToBookingDtoShort(Booking booking){
        return BookingDtoShort.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getStatus())
                .item(itemMapper.itemToItemVeryShort(booking.getItem()))
                .bookingId(booking.getId())
                .build();
    }


}
