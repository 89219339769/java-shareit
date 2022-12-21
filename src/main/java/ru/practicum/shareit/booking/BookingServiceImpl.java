package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import static ru.practicum.shareit.booking.BookingStatus.*;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final ItemMapper itemMapper;

    private final BookingMapper bookingMapper;

    @Override
    public BookingDtoShort saveBooking(long userId, Booking booking) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден бронирующий с номером: " + userId));

        Item item = itemRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно найти вещь с номером " + booking.getItem()));
        booking.setBooker(user);


        booking.setItem(item);
        booking.setStatus(WAITING);
        bookingRepository.save(booking);

        return bookingMapper.bookingToBookingDtoShort(booking);
//return  itemMapper.itemToItemVeryShort(item);
    }

    @Override
    public BookingDtoShort getById(long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Не найдена бронь с номером: " + bookingId));
        return bookingMapper.bookingToBookingDtoShort(booking);

    }


    @Override
    public BookingDtoShort approve(Long bookingId, Long userId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Не найдена бронь с номером: " + bookingId));
        Item item = booking.getItem();
        User owner = item.getOwner();
        Long ownerId = owner.getId();
        if (!ownerId.equals(userId)) {
            throw new NotFoundException("не найдено вещи владельца с номером " + userId);
        }
        if (approved = true)
            booking.setStatus(APPROVED);
        if (approved = false)
            booking.setStatus(REJECTED);
        bookingRepository.save(booking);

        return bookingMapper.bookingToBookingDtoShort(booking);
    }


}
