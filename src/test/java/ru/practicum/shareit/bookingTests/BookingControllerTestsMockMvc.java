package ru.practicum.shareit.bookingTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.RequestController;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTestsMockMvc {

    @MockBean
    BookingService bookingService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mvc;

    BookingDtoShort bookingDtoShort;


    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@mail.ru");

        Item item = new Item(1l, "test", "test", true, 2L, user);

        bookingDtoShort = BookingDtoShort.builder()
                .id(1L)
                .start(LocalDateTime.parse("2023-01-06T19:49:48"))
                .end(LocalDateTime.parse("2024-01-06T19:49:48"))
                .status(BookingStatus.WAITING)
                .booker(user)
                .item(item)
                .build();
    }


    @Test
    void createNewBooking() throws Exception {
        Mockito
                .when(bookingService.saveBooking(Mockito.anyLong(), Mockito.any()))
                .thenReturn(bookingDtoShort);


        mvc.perform(post("/bookings").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(bookingDtoShort))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDtoShort)));
    }
}