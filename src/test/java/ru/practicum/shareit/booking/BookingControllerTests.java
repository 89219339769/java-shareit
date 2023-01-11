package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.booking.model.BookingShortDtoWithItemId;
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingControllerTests {
    @Autowired
    private BookingController bookingController;

    @Autowired
    private ItemController itemController;
    @Autowired
    private UserController userController;


    private BookingDtoShort bookingDtoShort;

    User user;


    Item item;
    private BookingShortDtoWithItemId bookingShortDtoWithItemId;


    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@mail.ru");


        item = new Item(1L, "test", "test", true, null, user);

        bookingDtoShort = BookingDtoShort.builder()
                .id(1L)
                .start(LocalDateTime.of(2023, 12, 10, 10, 20, 10))
                .end(LocalDateTime.of(2024, 12, 20, 10, 20, 10))
                .status(BookingStatus.WAITING)
                .booker(user)
                .item(item)
                .build();


        bookingShortDtoWithItemId = BookingShortDtoWithItemId.builder()
                .id(1L)
                .start(LocalDateTime.of(2023, 12, 10, 10, 20, 10))
                .end(LocalDateTime.of(2024, 12, 20, 10, 20, 10))
                .itemId(1L)
                .bookerId(2L)
                .build();

    }


    @Test
    void createBookingTest() {

        userController.create(user);
        User user2 = new User();
        user2.setName("test2");
        user2.setEmail("test@mail2.ru");

        userController.create(user);
        userController.create(user2);
        itemController.add(1L, item);
        bookingController.add(2L, bookingShortDtoWithItemId);
        assertEquals(1L, bookingController.findBookingById(1L, 1L).getId());
    }


    @Test
    void findBookingById() {
        userController.create(user);
        User user2 = new User();
        user2.setName("test2");
        user2.setEmail("test@mail2.ru");

        userController.create(user);
        userController.create(user2);
        itemController.add(1L, item);
        bookingController.add(2L, bookingShortDtoWithItemId);
        bookingDtoShort = BookingDtoShort.builder()
                .id(1L)
                .start(LocalDateTime.of(2023, 12, 10, 10, 20, 10))
                .end(LocalDateTime.of(2024, 12, 20, 10, 20, 10))
                .status(BookingStatus.WAITING)
                .booker(user2)
                .item(item)
                .build();

        BookingDtoShort bookingDtoShorttest = bookingController.findBookingById(1L, 2L);
        assertEquals(bookingDtoShort, bookingDtoShorttest);
    }

    @Test
    void approveBooking() {
        userController.create(user);
        User user2 = new User();
        user2.setName("test2");
        user2.setEmail("test@mail2.ru");

        userController.create(user);
        userController.create(user2);
        itemController.add(1L, item);
        bookingController.add(2L, bookingShortDtoWithItemId);
        bookingController.approve(1L, 1L, true);
        bookingDtoShort.setBooker(user2);
        bookingDtoShort.setStatus(BookingStatus.APPROVED);
        BookingDtoShort bookingDtoShorttest = bookingController.findBookingById(1L, 2L);
        assertEquals(bookingDtoShort, bookingDtoShorttest);
    }

    @Test
    void getAllUsersBookingWithStatusAll() {
        userController.create(user);
        User user2 = new User();
        user2.setName("test2");
        user2.setEmail("test@mail2.ru");
        Item item2 = new Item(2L, "test", "test", true, null, user2);

        userController.create(user);
        userController.create(user2);

        itemController.add(1L, item);
        itemController.add(2L, item2);

        BookingShortDtoWithItemId bokingShortDtoWithItemId2 = BookingShortDtoWithItemId.builder()
                .start(LocalDateTime.of(2023, 12, 10, 10, 20, 10))
                .end(LocalDateTime.of(2024, 12, 20, 10, 20, 10))
                .itemId(2L)
                .bookerId(1L)
                .build();

        bookingController.add(2L, bookingShortDtoWithItemId);
        bookingController.add(1L, bokingShortDtoWithItemId2);

        List<Booking> bookings = bookingController.getAllByUser(2L, "ALL", 0, 30);
        String status = String.valueOf(bookings.get(0).getStatus());
        assertEquals(status, "WAITING");
    }

    @Test
    void getAllUsersBookingWithStatusRegected() {
        userController.create(user);
        User user2 = new User();
        user2.setId(2L);
        user2.setName("test21");
        user2.setEmail("test@mail112.ru");
        Item item2 = new Item(2L, "test", "test", true, null, user2);

        userController.create(user);
        userController.create(user2);

        itemController.add(1L, item);
        itemController.add(2L, item2);

        BookingShortDtoWithItemId bokingShortDtoWithItemId2 = BookingShortDtoWithItemId.builder()
                .start(LocalDateTime.of(2023, 12, 10, 10, 20, 10))
                .end(LocalDateTime.of(2024, 12, 20, 10, 20, 10))
                .itemId(2L)
                .bookerId(1L)
                .build();

        bookingController.add(2L, bookingShortDtoWithItemId);
        bookingController.add(1L, bokingShortDtoWithItemId2);
        bookingController.approve(2L, 2L, false);
        List<Booking> bookings = bookingController.getAllByUser(1L, "REJECTED", 0, 30);
        String statusTest = String.valueOf(bookings.get(0).getStatus());
        assertEquals(statusTest, "REJECTED");
    }

}