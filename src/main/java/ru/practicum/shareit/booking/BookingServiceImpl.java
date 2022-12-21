package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import static ru.practicum.shareit.booking.BookingStatus.WAITING;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Booking save(long userId, Booking booking) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден бронирующий с id: " + userId));

        Item item = itemRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно найьт вещь с номером " + booking.getItem()));
        booking.setBooker(user);
        booking.setItem(item);
        booking.setStatus(WAITING);
        return bookingRepository.save(booking);

    }


}
