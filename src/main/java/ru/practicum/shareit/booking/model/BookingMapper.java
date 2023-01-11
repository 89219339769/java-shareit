package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class BookingMapper {


    public static Booking toBooking(BookingShortDtoWithItemId bookingShortDto) {
        return Booking.builder()
                .id(bookingShortDto.getId())
                .start(bookingShortDto.getStart())
                .end(bookingShortDto.getEnd())
                .build();
    }


    public BookingDtoShort bookingToBookingDtoShort(Booking booking) {
        return BookingDtoShort.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getStatus())
                .item(booking.getItem())
                .booker(booking.getBooker())
                .build();
    }







}
