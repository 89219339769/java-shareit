package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.Booking;

public interface BookingService  {


    Booking save(long userId, Booking booking);
}
