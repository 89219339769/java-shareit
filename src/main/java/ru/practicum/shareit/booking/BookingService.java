package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.booking.model.BookingShortDtoWithItemId;

import java.util.List;

public interface BookingService {

    BookingDtoShort saveBooking(long userId, BookingShortDtoWithItemId bookingShortDto);

    BookingDtoShort getById(long userId, long bookingId);

    BookingDtoShort approve(Long bookingId, Long userId, Boolean approved);

    List<Booking> getAllBokingsSortByState(Long userId, String state);

    List<Booking> getAllBokingsByOwnerSortByState(Long userId, String state);
}
