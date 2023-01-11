package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
class BookingControllerTestJson {
    @Autowired
    JacksonTester<BookingDtoShort> json;

    @Test
    void testBookingDtoShort() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@mail.ru");


        Item item = new Item(1L, "test", "test", true, 2L, user);

        BookingDtoShort bookingDtoShort = BookingDtoShort.builder()
                .id(1L)
                .start(LocalDateTime.of(2022, 12, 10, 10, 20, 10))
                .end(LocalDateTime.of(2022, 12, 20, 10, 20, 10))
                .status(BookingStatus.WAITING)
                .booker(user)
                .item(item)
                .build();

        JsonContent<BookingDtoShort> result = json.write(bookingDtoShort);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo("WAITING");
        assertThat(result).extractingJsonPathStringValue("$.start")
                .isEqualTo(LocalDateTime.of(2022, 12, 10, 10, 20, 10).toString());
        assertThat(result).extractingJsonPathStringValue("$.end")
                .isEqualTo(LocalDateTime.of(2022, 12, 20, 10, 20, 10).toString());
    }
}



