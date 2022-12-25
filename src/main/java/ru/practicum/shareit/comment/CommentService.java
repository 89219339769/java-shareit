package ru.practicum.shareit.comment;

import java.util.Optional;

public interface CommentService {
    Comment addComment(Long userId, Long itemId, Comment comment);

    Optional<Comment> getCommentsByIetmId(Long itemId);
}
