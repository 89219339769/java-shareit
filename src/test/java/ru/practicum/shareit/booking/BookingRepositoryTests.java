package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.ShareItApp;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.booking.model.BookingShortDtoWithItemId;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static ru.practicum.shareit.booking.BookingStatus.WAITING;



@SpringBootTest(classes = ShareItApp.class)
class BookingRepositoryTests {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BookingController bookingController;

    @Autowired
    private ItemController itemController;
    @Autowired
    private UserController userController;
    BookingShortDtoWithItemId bookingShortDtoWithItemId;

    private BookingDtoShort bookingDtoShort;
    private User user;

    private Item item;

    private User user2;

    private Booking booking;


    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@mail.ru");


        item = new Item(1L, "test", "test", true, null, user);

        bookingDtoShort = BookingDtoShort.builder()
                .id(1L)
                .start(LocalDateTime.of(2023, 1, 10, 10, 30))
                .end(LocalDateTime.of(2023, 2, 10, 10, 30))
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

        booking = Booking.builder()
                .start(LocalDateTime.of(2023, 1, 10, 10, 30))
                .end(LocalDateTime.of(2023, 2, 10, 10, 30))
                .item(item)
                .booker(user2)
                .status(WAITING)
                .build();
    }

    @Test
    void approveBooking() {
        userRepository.save(user);
        User user2 = new User();
        user2.setName("test2");
        user2.setEmail("test@mail2.ru");

        userRepository.save(user2);

        itemRepository.save(item);
        booking.setBooker(user);
        bookingRepository.save(booking);
        bookingController.approve(1L, 1L, true);
        bookingDtoShort.setBooker(user);
        bookingDtoShort.setStatus(BookingStatus.APPROVED);

        BookingDtoShort bookingDtoShorttest = bookingController.findBookingById(1L, 1L);
        assertEquals(bookingDtoShort, bookingDtoShorttest);
    }


}