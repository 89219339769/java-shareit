
package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.BookingService;


@RestController
    @RequestMapping("/bookings")
    @RequiredArgsConstructor
    public class BookingController {
        private final BookingService bookingService;

        @PostMapping
        public Booking add(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @RequestBody Booking booking) {

            return     bookingService.save(userId, booking);




        }



}
