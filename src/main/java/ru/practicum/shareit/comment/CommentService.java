package ru.practicum.shareit.comment;

import org.springframework.stereotype.Component;

@Component
public interface CommentService {
    CommentDtoOut addComment(Long userId, Long itemId, Comment comment);

}
