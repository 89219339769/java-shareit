package ru.practicum.shareit.comment;

public interface CommentService {
    Comment addComment(Long userId, Long itemId, Comment comment);
}
