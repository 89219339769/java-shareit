package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDtoShort;

public interface BookingService  {


  //  Booking save(long userId, Booking booking);

    BookingDtoShort saveBooking(long userId, Booking booking);

    BookingDtoShort getById(long bookingId);
}
