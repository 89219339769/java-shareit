package ru.practicum.shareit.comment;

public interface CommentService {
    CommentDtoOut addComment(Long userId, Long itemId, Comment comment);

}
