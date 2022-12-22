package ru.practicum.shareit.booking.model;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemMapper;

@AllArgsConstructor
@Component
public class BookingMapper {
    private final ItemMapper itemMapper;

    public BookingShortDtoWithItemId toBookingShortDto(Booking booking) {
        return BookingShortDtoWithItemId.builder()
                .id(booking.getId())
                .bookerId(booking.getBooker().getId())
                .itemId(booking.getItem().getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    public static Booking toBooking(BookingShortDtoWithItemId bookingShortDto) {
        return Booking.builder()
                .id(bookingShortDto.getId())
                .start(bookingShortDto.getStart())
                .end(bookingShortDto.getEnd())
                .build();
    }


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
