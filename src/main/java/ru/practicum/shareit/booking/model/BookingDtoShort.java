package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@Builder
public class BookingDtoShort {

    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private BookingStatus status;

    private User booker;

    private Item item;




    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BookingDtoShort otherBookingDtoShort = (BookingDtoShort) obj;
        return Objects.equals(id, otherBookingDtoShort.id) &&
                Objects.equals(start, otherBookingDtoShort.start) &&
                Objects.equals(end, otherBookingDtoShort.end) &&
                Objects.equals(status, otherBookingDtoShort.status);


    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, status, booker, item);
    }


}






