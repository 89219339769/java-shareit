package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDtoShort;

import java.util.List;

public interface BookingService  {


  //  Booking save(long userId, Booking booking);

    BookingDtoShort saveBooking(long userId, Booking booking);

    BookingDtoShort getById(long bookingId);


    BookingDtoShort approve(Long bookingId, Long userId, Boolean approved);


    List<Booking>getAllBokingsSortByState(Long userId, String state);

}
