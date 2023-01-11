package ru.practicum.shareit.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDtoOut addComment(Long userId, Long itemId, Comment comment) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Невозможно найти вещь с id: " + itemId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("не найден пользователь с id: " + userId));

        comment.setAuthor(user);
        comment.setItem(item);
        comment.setCreated(LocalDateTime.now());

        if (item.getOwner().getId().equals(user.getId())) {
            throw new BadRequestException("Пользователь не может комментировать собственную вещь");
        }

        if (comment.getText().isBlank()) {
            throw new BadRequestException("Коментарий не может быть пустым");
        }

        Optional<Booking> booking = bookingRepository.findBookingByItemIdAndBookerIdAndEndIsBefore(
                itemId,
                userId,
                LocalDateTime.now());

        if (booking.isEmpty()) {
            throw new BadRequestException("Комментарии может оставить только пользователь," +
                    " который брал вещи, после завершения оренды");
        }
        commentRepository.save(comment);
        return commentMapper.toCommentDt0FromComment(comment);
    }
}



