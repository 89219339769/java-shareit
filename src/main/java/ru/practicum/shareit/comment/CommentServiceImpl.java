package ru.practicum.shareit.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Override
    public Comment addComment(Long userId, Long itemId, Comment comment) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Невозможно найти вещь с id: " + itemId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("не найден пользователь с id: " + userId));

        comment.setAuthor(user);
        comment.setItem(item);

//        if(item.getOwner().equals(user.getId())){
//
//            throw new BadRequestException("Пользователь не может комментировать собственную вещь");
//        }

        if (comment.getText().isBlank()) {
            throw new BadRequestException("Коментарий не может быть пустым");
        }

        commentRepository.save(comment);
        return comment;

    }


}
