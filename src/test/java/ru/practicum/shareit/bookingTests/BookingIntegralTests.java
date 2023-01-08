package ru.practicum.shareit.bookingTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.booking.model.BookingShortDtoWithItemId;
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingIntegralTests {
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


        item = new Item(1l, "test", "test", true, null, user);

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
                .itemId(1l)
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
    void FindBookingById() {
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
        assertEquals( bookingDtoShort, bookingDtoShorttest);

    }
}