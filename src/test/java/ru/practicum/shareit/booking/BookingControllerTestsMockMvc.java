package ru.practicum.shareit.booking;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    Booking booking;


    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@mail.ru");


        Item item = new Item(1L, "test", "test", true, 2L, user);

        bookingDtoShort = BookingDtoShort.builder()
                .id(1L)
                .start(LocalDateTime.parse("2023-01-06T19:49:48"))
                .end(LocalDateTime.parse("2024-01-06T19:49:48"))
                .status(BookingStatus.WAITING)
                .booker(user)
                .item(item)
                .build();

        booking = Booking.builder()
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


    @Test
    void getBookingById() throws Exception {
        Mockito
                .when(bookingService.getById(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(bookingDtoShort);

        mvc.perform(get("/bookings/1").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(bookingDtoShort))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDtoShort)));

    }

    @Test
    void approveBookingById() throws Exception {
        bookingDtoShort.setStatus(BookingStatus.APPROVED);

        Mockito
                .when(bookingService.approve(Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
                .thenReturn(bookingDtoShort);

        mvc.perform(patch("/bookings/1?approved=true").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(bookingDtoShort))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDtoShort)));

    }


    @Test
    void getAllUsersBookings() throws Exception {


        Mockito
                .when(bookingService.getAllBokingsSortByState(Mockito.anyLong(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(booking));

        mvc.perform(get("/bookings").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(List.of(booking)))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(booking))));

    }


    @Test
    void getAllOwnerBookings() throws Exception {


        Mockito
                .when(bookingService.getAllBokingsByOwnerSortByState(Mockito.anyLong(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(booking));

        mvc.perform(get("/bookings/owner").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(List.of(booking)))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(booking))));

    }


}