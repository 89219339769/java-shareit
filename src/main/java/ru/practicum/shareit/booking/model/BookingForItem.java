package ru.practicum.shareit.booking.model;

import lombok.*;

import java.time.LocalDateTime;


@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class BookingForItem {
        private long id;
        private long bookerId;
        private LocalDateTime start;
        private LocalDateTime end;
    }






